package com.backend.Controller;


//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {

    @GetMapping("/hello-1")
    public String hello1(){
        return "Hellow";
    }

    @GetMapping("/hello-2")
    public String hello2(){
        System.out.println("ADMIN can access");
        return "ADMIN can access";
    }

    @GetMapping("/hello-3")
    public String hello3(){
        return "USER can access";
    }

    @GetMapping("/hello-4")
    public String hello4(){
        return "ADMIN/ USER can access";
    }
}