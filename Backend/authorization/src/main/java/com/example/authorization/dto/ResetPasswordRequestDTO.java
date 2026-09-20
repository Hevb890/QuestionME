package com.example.authorization.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequestDTO(
    @NotBlank String email,
    @NotBlank String newPassword,
    @NotBlank String confirmPassword
) {
    
}
