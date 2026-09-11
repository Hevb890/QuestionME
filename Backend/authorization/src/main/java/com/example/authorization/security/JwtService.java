package com.example.authorization.security;

import com.example.authorization.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.keystore.location}")
    private String keystoreLocation;

    @Value("${app.keystore.password}")
    private String keystorePassword;

    @Value("${app.keystore.alias}")
    private String keyAlias;

    @Value("${app.jwt.issuer:http://localhost:8080}")
    private String issuer;

    private final ResourceLoader resourceLoader;
    private PrivateKey privateKey;

    public JwtService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() {
        System.out.println("====== JWT SERVICE KEYSTORE TROUBLESHOOTING ======");
        System.out.println("Target Keystore Location: " + keystoreLocation);
        System.out.println("Target Keystore Alias:    " + keyAlias);
        System.out.println("==================================================");
        
        try {
            if (keystoreLocation == null || keystoreLocation.trim().isEmpty()) {
                throw new IllegalArgumentException("Keystore location variable resolved to empty or null.");
            }
            
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            org.springframework.core.io.Resource resource = resourceLoader.getResource(keystoreLocation);
            
            System.out.println("Resource Exists Checklist: " + resource.exists());
            
            try (InputStream is = resource.getInputStream()) {
                keyStore.load(is, keystorePassword.toCharArray());
            }
            
            this.privateKey = (PrivateKey) keyStore.getKey(keyAlias, keystorePassword.toCharArray());
            
            if (this.privateKey == null) {
                throw new IllegalArgumentException("Keystore loaded successfully, but the alias '" + keyAlias + "' was not found inside it!");
            }
            
            System.out.println("SUCCESS: Cryptographic Private Key verified and loaded!");
            
        } catch (Exception e) {
            System.err.println("CRITICAL FAILURE ON INITIALIZATION:");
            e.printStackTrace(); 
            throw new IllegalStateException("Could not load private key from keystore path: " + keystoreLocation, e);
        }
    }


    public String generateToken(User user) {
        long expirationMs = 86400000; // 24 hours

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}