package com.lomash.mytrip.controller;


import com.lomash.mytrip.dto.auth.LoginRequest;
import com.lomash.mytrip.dto.auth.LoginResponse;
import com.lomash.mytrip.dto.auth.RegisterRequest;
import com.lomash.mytrip.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {


    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok().body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}