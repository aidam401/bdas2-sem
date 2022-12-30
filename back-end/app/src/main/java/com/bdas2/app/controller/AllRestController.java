package com.bdas2.app.controller;


import com.bdas2.app.repository.CrudRepository;
import com.bdas2.app.repository.LoginRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@RestController
@Slf4j
public class AllRestController {
    final CrudRepository crudRepo;
    final LoginRepository loginRepo;

    public AllRestController(CrudRepository repo, LoginRepository loginRepo) {
        this.crudRepo = repo;
        this.loginRepo = loginRepo;
    }

    @GetMapping(value = "/{endpointName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allEndpoint(@PathVariable String endpointName, @RequestParam(required = false) Map<String, Object> paramMap) {
        try {
            if (Objects.equals(endpointName, "login")) {
                return loginEndpoint((String) paramMap.getOrDefault("name", null), (String) paramMap.getOrDefault("password", null));
            }
            if (endpointName.substring(0, 5).equalsIgnoreCase("count")){
                return countEndpoints(endpointName.substring(5));
            }
            if (endpointName.substring(0, 3).equalsIgnoreCase("all")) {
                return allEndpoints(endpointName.substring(3), Integer.parseInt((String) paramMap.getOrDefault("limit", "-1")), Integer.parseInt((String) paramMap.getOrDefault("offset", "-1")));
            }
            if (endpointName.substring(0, 6).equalsIgnoreCase("detail") && paramMap.containsKey("id")) {
                return detailEndpoints(endpointName.substring(6), Integer.parseInt((String) paramMap.get("id")));
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> allEndpoints(@NonNull String table, @Nullable Integer limit, @Nullable Integer offset){
        if(limit != -1 && offset != -1){
            return new ResponseEntity<>(crudRepo.fetchAll(table, limit, offset).toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(crudRepo.fetchAll(table).toString(), HttpStatus.OK);
    }
    private ResponseEntity<String> countEndpoints(@NonNull String table){
        return new ResponseEntity<>(String.valueOf(crudRepo.fetchCount(table)), HttpStatus.OK);
    }
    private ResponseEntity<String> detailEndpoints(@NonNull String table, @NonNull Integer id){
        return new ResponseEntity<>(crudRepo.fetchDetail(table, id).toString(), HttpStatus.OK);
    }

    private ResponseEntity<String> loginEndpoint(@NonNull String name, @NonNull String password){
        return new ResponseEntity<>(String.valueOf(loginRepo.userExist(name, password)), HttpStatus.OK);
    }


}
