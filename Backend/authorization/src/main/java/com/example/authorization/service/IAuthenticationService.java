package com.example.authorization.service;

import com.example.authorization.dto.AuthenticationResponse;
import com.example.authorization.dto.LoginRequest;
import com.example.authorization.dto.RegisterRequest;

public interface IAuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse login(LoginRequest request);
}
