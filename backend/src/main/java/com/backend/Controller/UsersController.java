package com.backend.Controller;

import com.backend.Entity.Users;
import com.backend.Service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class UsersController {

    @Autowired
    private UserServices userServices;
    @PostMapping("v1/users/register")
    public ResponseEntity<Object> addTask(@RequestBody Users users) {
        return userServices.addUser(users);
    }

    @PostMapping("v1/users/login")
    public ResponseEntity<Object> getOTP(@RequestBody Users data) {
        return userServices.getOTP(data);
    }
    public static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("v1/me")
    public Users getUserExtra(Principal principal) {
        System.out.println("principal => "+principal);
        return userServices.validateAndGetUserExtra(principal.getName());
    }

//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
//    @PostMapping("/me")
//    public Users saveUserExtra(@Valid @RequestBody UserExtraRequest updateUserExtraRequest,
//                                   Principal principal) {
//        Optional<Users> userExtraOptional = userExtraService.getUserExtra(principal.getName());
//        Users userExtra = userExtraOptional.orElseGet(() -> new Users(  ));
//        userExtra.setAvatar(updateUserExtraRequest.getAvatar());
//        return userExtraService.saveUserExtra(userExtra);
//    }
}
