package com.backend.SuccessMessage;

import com.backend.Entity.Task;
import com.backend.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
@AllArgsConstructor
@Getter
@Component
@NoArgsConstructor
public class SuccessMessageForUser {

    private String message;
    private Users users;
    private HttpStatus httpStatus;
    private int otpValue;

    public SuccessMessageForUser(String message, HttpStatus httpStatus, int otpValue) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.otpValue = otpValue;
    }
}
