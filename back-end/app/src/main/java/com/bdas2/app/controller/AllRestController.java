package com.bdas2.app.controller;


import com.bdas2.app.repository.CrudRepository;
import com.bdas2.app.repository.LoginRepository;
import com.bdas2.app.repository.SpecialRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;


@RestController
@Slf4j
@CrossOrigin
public class AllRestController {
    final CrudRepository crudRepo;
    final LoginRepository loginRepo;
    final SpecialRepository specialRepository;

    public AllRestController(CrudRepository repo, LoginRepository loginRepo, SpecialRepository specialRepository) {
        this.crudRepo = repo;
        this.loginRepo = loginRepo;
        this.specialRepository = specialRepository;
    }


    @ResponseBody
    @RequestMapping(value = "/{tableName}/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> overTableRouterEndpoint(@PathVariable String tableName,
                                                          @PathVariable String keyword,
                                                          @RequestParam(required = false) Map<String, Object> params,
                                                          @RequestBody(required = false) String body,
                                                          HttpServletRequest request) {
        try {
            var method = request.getMethod();

            //CRUD
            if (Objects.equals(method, "POST") && Objects.equals(keyword.toLowerCase(), "create")) {
                return create(tableName, body);
            }
            if (Objects.equals(method, "GET") && Objects.equals(keyword.toLowerCase(), "read")) {
                return read(
                        tableName,
                        Integer.parseInt((String) params.getOrDefault("limit", "-1")),
                        Integer.parseInt((String) params.getOrDefault("offset", "-1")),
                        Integer.parseInt((String) params.getOrDefault("id", "-1")),
                        (String) params.getOrDefault("query", ""));
            }
            if (Objects.equals(method, "POST") && Objects.equals(keyword.toLowerCase(), "update")) {
                return update(tableName, Integer.parseInt((String) params.get("id")), body, (String) params.getOrDefault("idCol", "-1"));
            }
            if (Objects.equals(method, "DELETE") && Objects.equals(keyword.toLowerCase(), "delete")) {
                return delete(tableName, Integer.parseInt((String) params.get("id")));
            }

            //Special
            if (Objects.equals(method, "GET") && Objects.equals(keyword.toLowerCase(), "count")) {
                return countEndpoints(tableName);
            }
            return new ResponseEntity<>("Endpoint nenalezen", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<String> loginEndpoint(@RequestParam String name, @RequestParam String password, @RequestParam(required = false) String anotherUser) {
        try {
            return new ResponseEntity<>(String.valueOf(loginRepo.userExist(name, password, anotherUser)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/linky", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> linky(@RequestParam String nazev, @RequestParam Integer limit, @RequestParam Integer offset) {
        try {
            return new ResponseEntity<>(specialRepository.linky(nazev, limit, offset).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/linkyDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> linkyDetail(@RequestParam Integer id) {
        try {
            return new ResponseEntity<>(specialRepository.linkyDetail(id).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/spoje", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> spoje(@RequestParam String nazev, @RequestParam Integer limit, @RequestParam Integer offset) {
        try {
            return new ResponseEntity<>(specialRepository.spoje(nazev, limit, offset).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/spojeDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> spojeDetail(@RequestParam Integer id) {
        try {
            return new ResponseEntity<>(specialRepository.spojeDetail(id).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/jizdy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> jizdy(@RequestParam String nazev, @RequestParam Integer limit, @RequestParam Integer offset) {
        try {
            return new ResponseEntity<>(specialRepository.jizdy(nazev, limit, offset).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/jizdyDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> jizdyDetail(@RequestParam Integer id) {
        try {
            return new ResponseEntity<>(specialRepository.jizdyDetail(id).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<String> create(@NonNull String tableName, @NonNull String body) {
        try {
            if (tableName.toLowerCase() == "uzivatel")
                return new ResponseEntity<>(String.valueOf(crudRepo.createUser(new JSONObject(body))), HttpStatus.OK);
            return new ResponseEntity<>(String.valueOf(crudRepo.create(tableName, new JSONObject(body))), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> read(@NonNull String table, Integer limit, Integer offset, Integer id, String query) {
        try {
            if (id != -1)
                return new ResponseEntity<>(crudRepo.fetchDetail(table, id).toString(), HttpStatus.OK);
            return new ResponseEntity<>(crudRepo.fetchAll(table, limit, offset, query).toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> update(@NonNull String tableName, @NonNull Integer id, @NonNull String body, @NonNull String idCol) {
        try {
            if ("-1".equals(idCol))
                idCol = null;
            return new ResponseEntity<>(String.valueOf(crudRepo.update(tableName, id, new JSONObject(body), idCol)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> delete(@NonNull String tableName, @NonNull Integer id) {
        try {
            return new ResponseEntity<>(String.valueOf(crudRepo.delete(tableName, id)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> countEndpoints(@NonNull String table) {
        try {
            return new ResponseEntity<>(String.valueOf(crudRepo.fetchCount(table)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
