package com.example.authorization.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyOtpDTO(@NotBlank int otp) {
}
