package com.example.authorization.controller;

import com.example.authorization.dto.AuthenticationResponse;
import com.example.authorization.dto.ForgetPasswordRequest;
import com.example.authorization.dto.RegisterRequest;
import com.example.authorization.dto.VerifyOtpRequest;
import com.example.authorization.service.IEmailService;
import com.example.authorization.service.IOtpService;
import com.example.authorization.service.impl.AuthenticationServiceImpl;
import com.example.authorization.dto.LoginRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final IEmailService emailService;
    private final IOtpService otpService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request){
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request){
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> requestOtp(@RequestBody ForgetPasswordRequest request){
        if (request.getEmail() == null || request.getEmail().isBlank()){
            return ResponseEntity.badRequest().body("Email cannot be empty.");
        }

        if (request.getUsername() == null || request.getUsername().isBlank()){
            return ResponseEntity.badRequest().body("Username cannot be empty.");
        }

        emailService.sendEmail(request.getEmail(), request.getUsername());

        return ResponseEntity.ok("OTP successfully and sent to " + request.getEmail());
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request){
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email cannot be empty.");
        }
        if (request.getOtp() == null || request.getOtp().isBlank()) {
            return ResponseEntity.badRequest().body("OTP cannot be empty.");
        }

        boolean isVerified = otpService.verifyOtp(request.getEmail(),request.getOtp());

        if (isVerified) {
            return ResponseEntity.ok("OTP verified successfully. You may now proceed to reset your password.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
        }
    }
}
