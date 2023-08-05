package com.backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

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


}
