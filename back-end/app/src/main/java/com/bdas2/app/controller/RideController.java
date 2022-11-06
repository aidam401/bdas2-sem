package com.bdas2.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ride")
public class RideController {
    @GetMapping("/allActualRides")
    public void getAllActualRides() {

    }

    @GetMapping("/actualRideDetail")
    public void getActualRideDetail() {

    }

}
