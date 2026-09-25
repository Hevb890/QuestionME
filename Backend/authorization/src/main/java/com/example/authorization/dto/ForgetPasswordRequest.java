package com.example.authorization.dto;

import lombok.Data;

@Data 
public class ForgetPasswordRequest {
    private String email;
    private String username;
}
