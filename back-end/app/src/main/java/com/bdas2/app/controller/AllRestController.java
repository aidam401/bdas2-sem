package com.bdas2.app.controller;


import com.bdas2.app.repository.CrudRepository;
import com.bdas2.app.repository.LoginRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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

    public AllRestController(CrudRepository repo, LoginRepository loginRepo) {
        this.crudRepo = repo;
        this.loginRepo = loginRepo;
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
                return update(tableName, Integer.parseInt((String) params.get("id")), body, (String) params.get("idCol"));
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
            return login(name, password, anotherUser);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> create(@NonNull String tableName, @NonNull String body) {
        return new ResponseEntity<>(String.valueOf(crudRepo.create(tableName, new JSONObject(body))), HttpStatus.OK);
    }

    private ResponseEntity<String> read(@NonNull String table, Integer limit, Integer offset, Integer id, String query) {
        if (id != -1)
            return new ResponseEntity<>(crudRepo.fetchDetail(table, id).toString(), HttpStatus.OK);
        return new ResponseEntity<>(crudRepo.fetchAll(table, limit, offset, query).toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> update(@NonNull String tableName, @NonNull Integer id, @NonNull String body, @NonNull String idCol) {
        if("-1".equals(idCol))
            idCol = null;
        return new ResponseEntity<>(String.valueOf(crudRepo.update(tableName, id, new JSONObject(body), idCol)), HttpStatus.OK);
    }

    private ResponseEntity<String> delete(@NonNull String tableName, @NonNull Integer id) {
        return new ResponseEntity<>(String.valueOf(crudRepo.delete(tableName, id)), HttpStatus.OK);
    }

    private ResponseEntity<String> countEndpoints(@NonNull String table) {
        return new ResponseEntity<>(String.valueOf(crudRepo.fetchCount(table)), HttpStatus.OK);
    }

    private ResponseEntity<String> login(@NonNull String name, @NonNull String password,String anotherUser) {
        return new ResponseEntity<>(String.valueOf(loginRepo.userExist(name, password, anotherUser)), HttpStatus.OK);
    }


}
