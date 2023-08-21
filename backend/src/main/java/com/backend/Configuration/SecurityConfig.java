//package com.backend.Configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
//        System.out.println("here comming");
//        return
//                httpSecurity.authorizeHttpRequests(authorize ->
//                                authorize
//                                        .requestMatchers(HttpMethod.GET, "/hello").hasRole("developer")
//                                        .anyRequest().authenticated()
//                        )
//                        .oauth2ResourceServer(oauth2 -> oauth2.
//                                jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
//                        ).build();
//
//    }
//
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String jwkSetUri = "http://localhost:8080/realms/new_demo/protocol/openid-connect/certs";
//
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
//                .jwsAlgorithm(SignatureAlgorithm.RS256)  // Use the appropriate JWS algorithm
//                .build();
//
//        return jwtDecoder;
//    }
//}
