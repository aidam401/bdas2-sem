package com.bdas2.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    @PostMapping("/login")
    public HttpStatus login() {
        return HttpStatus.OK;
    }

    @PostMapping("/logout")
    public HttpStatus logout() {
        return HttpStatus.OK;
    }

    @PostMapping("/register")
    public HttpStatus register(){
        return HttpStatus.OK;
    }

}
