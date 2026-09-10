package com.example.authorization.service;

import com.example.authorization.dto.AuthResponse;
import com.example.authorization.dto.RegisterRequest;
import com.example.authorization.entity.Role;
import com.example.authorization.entity.User;
import com.example.authorization.repository.UserRepository;
import com.example.authorization.security.JwtService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Email address is already in use.");
        }

        User user = User.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .roles(List.of(Role.USER))
            .build();
        
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken, user.getEmail());
    }
}
