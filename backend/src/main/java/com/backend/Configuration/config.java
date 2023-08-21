package com.backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class config {


    @Bean
    public String myStringBean() {
        return "Hello, World!";
    }
    @Bean
    public HttpStatus myHttpStatusBean() {
        return HttpStatus.OK;
    }

    @Bean
    public Integer myIntegerBean() {
        return 0;
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "http://localhost:8080/realms/keycloak/protocol/openid-connect/certs";

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
                .jwsAlgorithm(SignatureAlgorithm.RS256)  // Use the appropriate JWS algorithm
                .build();

        return jwtDecoder;
    }

}



