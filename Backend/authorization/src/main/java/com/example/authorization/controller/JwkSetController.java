package com.example.authorization.controller;

import com.example.authorization.security.JwkUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwkSetController {

    private final JwkUtils jwkUtils;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwkSet() {
        return jwkUtils.getJwkSet();
    }
}