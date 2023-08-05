package com.backend.ExceptionHandler;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
    public TaskNotFoundException(String message,String status) {
        super(message);
    }
    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

