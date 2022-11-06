package com.bdas2.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trolley")
public class TrolleyController {
    @GetMapping("allTrolley")
    public void getAllTrolleybuses(){

    }

    @GetMapping("trolleyDetail")
    public void getTrolleybusDetail(){


    }
}
