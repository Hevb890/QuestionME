package com.example.authorization.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Component
public class JwkUtils {
    @Value("${app.keystore.location}")
    private Resource keystoreLocation;

    @Value("${app.keystore.password}")
    private String keystorePassword;

    @Value("${app.keystore.alias}")
    private String keyAlias;

    private JWKSet jwkSet;

    @PostConstruct
    public void init() {
        try (InputStream is = keystoreLocation.getInputStream()) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(is, keystorePassword.toCharArray());

            Certificate cert = keyStore.getCertificate(keyAlias);
            RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .keyID(keyAlias)
                    .build();

            this.jwkSet = new JWKSet(rsaKey);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load public key for JWKS endpoint", e);
        }
    }

    public Map<String, Object> getJwkSet() {
        return this.jwkSet.toJSONObject();
    }
}
