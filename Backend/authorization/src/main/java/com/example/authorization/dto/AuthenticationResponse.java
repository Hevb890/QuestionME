package com.example.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String tokenType = "Bearer";

    public AuthenticationResponse(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }
}