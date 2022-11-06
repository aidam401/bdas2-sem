package com.bdas2.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/buses")
public class BusController {
    @GetMapping("/allBusses")
    public void getAllBuses(){

    }
    @GetMapping("/busDetail")
    public void getBusDetail(){

    }

}
