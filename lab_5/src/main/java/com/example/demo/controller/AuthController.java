package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Login endpoint, accepts username and password, and returns JWT
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Log to ensure the method is being called and credentials are correct.
        System.out.println("Logging in user: " + loginRequest.getUsername());

        // Call the login service method to validate the user and generate a JWT token.
        return authService.loginUser(loginRequest);
    }

    // Register endpoint, allows users to register a new account
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Call the register service method to register a new user in the system
        return authService.registerUser(registerRequest);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.status(HttpStatus.OK).body("You have logged out successfully!");
    }
}
