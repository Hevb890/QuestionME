package com.example.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.authorization.service.IEmailService;

@Service 
public class EmailServiceImpl implements IEmailService {

    @Autowired 
    private JavaMailSender mailSender;

    // Sending OTP for password reset
    @Override 
    public void sendEmail(String recipientEmail, String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Password Reset");
        message.setText("Hi " + username + ",\n\nPlease use the following OTP to reset your password: " + generateOTP());

        mailSender.send(message);
    }


    // Generate a random 6-digit OTP
    private int generateOTP() {
        return (int)(Math.random() * 900000) + 100000;
    }
}
