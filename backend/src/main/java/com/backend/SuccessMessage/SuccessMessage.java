package com.backend.SuccessMessage;

import com.backend.Entity.Task;
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

public class SuccessMessage {
    private String message;
    private HttpStatus httpStatus;
    private List<Task> data=null;
    private int count=0;

    public SuccessMessage(String message, HttpStatus httpStatus, int count) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.count = count;
    }
}


