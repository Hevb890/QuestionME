package com.example.authorization.service;

import java.util.Optional;

public interface IOtpService {
    String generateAndSaveOtp(String email);
    Optional<String> getOtp(String email);
    boolean verifyOtp(String email, String userSubmittedOtp);
}
