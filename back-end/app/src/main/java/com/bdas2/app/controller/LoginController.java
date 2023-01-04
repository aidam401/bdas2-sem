package com.bdas2.app.controller;

import com.bdas2.app.repository.modelRepositories.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping
public class LoginController {
    final LoginRepository repo;

    public LoginController(LoginRepository repo) {
        this.repo = repo;
    }
    @PostMapping("/login")
    public ResponseEntity userExist(@RequestParam String user,
                                    @RequestParam String password,
                                    @RequestParam(required = false) String anotherUsername) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.userExist(user, password, anotherUsername));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }


    }
}
