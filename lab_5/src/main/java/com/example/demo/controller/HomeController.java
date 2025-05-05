package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("/hi")
    @PreAuthorize("isAuthenticated()")
    public String hello() {
        return "Hi authenticated user!";
    }

    @GetMapping("/public")
    public String allAccess() {
        return "Public request! you are not authenticated";
    }

}