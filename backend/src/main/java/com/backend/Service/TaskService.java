package com.backend.Service;

import com.backend.Entity.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    ResponseEntity<Object> addTask(Task task);
    ResponseEntity<Object> getTask(Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskById(Long id);

    ResponseEntity<Object> getTaskByAssignedDate(String assigned_date, String assigned_by, String assigned_to, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskByAssignedDate(String assigned_date);

    ResponseEntity<Object> getTask(Long taskId);

    ResponseEntity<Object> getTask(String userName, Integer page, Integer pageSize);


    ResponseEntity<Object> getTaskByDue(String deadline, String assignedBy, String assignedTo, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskByDue(String deadline);

    ResponseEntity<Object> getTaskByPriority(String priority);

    ResponseEntity<Object> getTaskByIsCompleted(boolean isCompleted);

    ResponseEntity<Object> deleteTaskById(Long taskId);

    ResponseEntity<Object> deleteTaskByAssignedDate(String assignedDate);

    ResponseEntity<Object> deleteTaskByDue(String deadline);

    ResponseEntity<Object> deleteTaskByPriority(String priority);

    ResponseEntity<Object> deleteTaskByIsCompleted(boolean isCompleted);

    ResponseEntity<Object> updateTask(Long task_id, Task data);

    ResponseEntity<Object> addTaskV2(Task task);


    ResponseEntity<Object> getTaskByAssignedDateStartAfter(String assignedDate,Integer page,Integer pageSize);
    ResponseEntity<Object> getTaskByAssignedDateStartAfter(String assignedDate,String assignedBy,String assignedTo,Integer page,Integer pageSize);

    ResponseEntity<Object> getTaskByAssignedDateEndAfter(String due,Integer page,Integer pageSize);
    ResponseEntity<Object> getTaskByAssignedDateEndAfter(String due,String assignedBy,String assignedTo,Integer page,Integer pageSize);

    ResponseEntity<Object> getTaskWithPrefix(String prefix, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskWithCreatedAndPrefixForUpcoming(String prefix, String created, String assignedBy, String assignedTo, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskWithDueAndPrefixForUpcoming(String prefix, String due, String assignedBy, String assignedTo, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskByAssignedDateWithPrefix(String prefix, String assignedDate, String assignedBy, String assignedTo, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskByDueWithPrefix(String prefix, String deadline, String assignedBy, String assignedTo, Integer page, Integer pageSize);

    ResponseEntity<Object> getTodayTaskByDue(String today, String deadline, String assignedBy,String assigned_to, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskByAssignedDateV2(String assignedDate, String assignedBy, String assignedTo, Integer page, Integer pageSize);

    ResponseEntity<Object> getTaskByDueV2(String deadline, String assignedBy, String assignedTo, Integer page, Integer pageSize);


//    void sendOTPToEmail(String mail);

//    ResponseEntity<Object> getTaskWithCreatedAndForUpcoming(String assignedDate, String assignedBy, String assignedTo, Integer page, Integer pageSize);
//
//    ResponseEntity<Object> getTaskWithDueAndForUpcoming(String due, String assignedBy, String assignedTo, Integer page, Integer pageSize);
}

