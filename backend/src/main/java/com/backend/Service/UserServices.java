package com.backend.Service;

import com.backend.Entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {
    ResponseEntity<Object> addUser(Users users);

    ResponseEntity<Object> getOTP(Users data);

    Users validateAndGetUserExtra(String name);
}

