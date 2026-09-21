package com.example.authorization.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.authorization.service.IOtpService;

@Service 
public class OtpServiceImpl implements IOtpService {
    
    private final Map<String, OtpData> otpCache = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    private static final long OTP_VALIS_MINUTES = 5;

    @Override 
    public String generateAndSaveOtp(String email){
        int number = 100000 + secureRandom.nextInt(900000);
        String otp = String.valueOf(number);

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_VALIS_MINUTES);
        otpCache.put(email, new OtpData(otp, expiryTime));

        return otp;
    }

    @Override 
    public Optional<String> getOtp(String email){
        OtpData data = otpCache.get(email);
        if(data == null){
            return Optional.empty();
        }

        if(LocalDateTime.now().isAfter(data.expiryTime())){
            otpCache.remove(email);
            return Optional.empty();
        }

        return Optional.of(data.otp());
    }

    

    @Override 
    public boolean verifyOtp(String email, String userSubmittedOtp){
        Optional<String> correctOtp = getOtp(email);

        if(correctOtp.isEmpty()){
            return false;
        }

        if (correctOtp.get().equals(userSubmittedOtp)){
            otpCache.remove(email);
            return true;
        }

        return false;
    }

    public record OtpData(String otp, LocalDateTime expiryTime) {
    }
}
