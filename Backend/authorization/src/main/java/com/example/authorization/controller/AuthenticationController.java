package com.example.authorization.controller;

import com.example.authorization.dto.AuthResponse;
import com.example.authorization.dto.RegisterRequest;
import com.example.authorization.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestController
@RequestMapping("/api/v1/auth")
@RequestArgsConstructor
public class AuthenticationController {
    private final AuthentiactionService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        AuthResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
