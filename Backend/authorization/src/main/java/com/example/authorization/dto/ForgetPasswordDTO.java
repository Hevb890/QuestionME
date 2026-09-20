package com.example.authorization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordDTO(@NotBlank @Email String email) {
}
