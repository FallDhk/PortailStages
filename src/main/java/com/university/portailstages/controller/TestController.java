package com.university.portailstages.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")

public class TestController {
    @GetMapping
    public String test() {
        return "Accès sécurisé OK";
    }
}
