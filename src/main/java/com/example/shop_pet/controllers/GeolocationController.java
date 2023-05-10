package com.example.shop_pet.controllers;

import com.example.shop_pet.models.Address;
import com.example.shop_pet.services.Geolocation.GeolocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class GeolocationController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeolocationService geolocationService;

    @GetMapping("/geolocation")
    public Address getGeolocation(@RequestParam double latitude, @RequestParam double longitude) throws Exception {
        logger.info("LocationService getGeolocation() is running...");
        return geolocationService.getAddress(latitude, longitude);
    }
}
