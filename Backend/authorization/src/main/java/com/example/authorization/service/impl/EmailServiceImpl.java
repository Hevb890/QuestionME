package com.example.authorization.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.authorization.service.IEmailService;
import com.example.authorization.service.IOtpService;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;
    private final IOtpService otpService;

    @Override 
    public void sendEmail(String recipientEmail, String username){
        String otp = otpService.generateAndSaveOtp(recipientEmail);

        SimpleMailMessage message  = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Password Reset");
        message.setText("Hi " + username + ",\n\nPlease use the following OTP to reset your password: " + otp);
        mailSender.send(message);
    }
}
