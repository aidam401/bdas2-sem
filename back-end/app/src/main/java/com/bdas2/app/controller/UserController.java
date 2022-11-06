package com.bdas2.app.controller;

import com.bdas2.app.dto.User;
import com.bdas2.app.exception.UserServiceException;
import com.bdas2.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loggedUserDetail")
    public List<User> getLoggedUserDetail(){
        return null;
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/userDetail")
    public void getUserDetail(){

    }
    @GetMapping("/userImage")
    public void getUserImage(){

    }

    @PostMapping("/changeUserImage")
    public void changeUserImage(){

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(UserServiceException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
