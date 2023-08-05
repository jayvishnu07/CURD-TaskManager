package com.backend.SuccessMessage;

import com.backend.Entity.Task;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@NoArgsConstructor
public class SuccessHandler {

    public ResponseEntity<Object> successMessageHandler(String message, List<Task> task){
        int count=0;
        if(task != null){
            count = task.size();
        }
        SuccessMessage successMessage1 = new SuccessMessage(message, HttpStatus.OK,task,count);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage1);
    }

    public ResponseEntity<Object> successMessageHandler(String message, List<Task> task,int count){

        SuccessMessage successMessage1 = new SuccessMessage(message, HttpStatus.OK,task,count);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage1);
    }

    public ResponseEntity<Object> successMessageHandler(String message, int count){
        SuccessMessage successMessage1 = new SuccessMessage(message, HttpStatus.OK,count);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage1);
    }
}
