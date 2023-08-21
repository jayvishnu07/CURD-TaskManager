package com.backend.SuccessMessage;

import com.backend.Entity.Users;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class SuccessMessageHandlerForUser {

    public ResponseEntity<Object> successMessageHandlerForUser(String message, Users users, int otpValue){
        SuccessMessageForUser successMessage = new SuccessMessageForUser(message,users, HttpStatus.OK,otpValue);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }
    public ResponseEntity<Object> successMessageHandlerForUser(String message, int otpValue){
        SuccessMessageForUser successMessage = new SuccessMessageForUser(message, HttpStatus.OK,otpValue);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }
}


