package com.backend.Service;

import com.backend.Entity.Task;
import com.backend.ExceptionHandler.TaskNotFoundException;
import com.backend.Repository.TaskRepository;
import com.backend.SuccessMessage.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class TaskServiceImplementation implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private final SuccessHandler successHandler;

    @Autowired
    public TaskServiceImplementation(SuccessHandler successHandler)   {
        this.successHandler = successHandler;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public ResponseEntity<Object> addTask(Task data) {
        try {
            LocalDate.parse((data.getDue().trim()), formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd");
        }

        if ((data.getDue() != null) && (LocalDate.parse(data.getDue(), formatter1).isAfter(LocalDate.now()))) {
            data.setDue(data.getDue());
        } else {
            throw new TaskNotFoundException("Invalid Due date.");
        }
        if ((data.getAssignedDate() != null) && (LocalDate.parse(data.getAssignedDate(), formatter1).isAfter(LocalDate.now()))) {
            data.setAssignedDate(data.getAssignedDate());
        } else {
            throw new TaskNotFoundException("Invalid Due date.");
        }
        if (data.getAssignedTo() == null || data.getAssignedBy() == null || data.getTaskTitle() == null || data.getPriority() == null ||
                data.getAssignedTo().length() == 0 || data.getAssignedBy().length() == 0 || data.getTaskTitle().length() == 0
        ) {
            throw new TaskNotFoundException("Invalid Data.");
        }
        if (!(data.getPriority()).equalsIgnoreCase("low") && !(data.getPriority()).equalsIgnoreCase("normal") && !(data.getPriority()).equalsIgnoreCase("high")) {
            throw new TaskNotFoundException("Invalid priority type.");
        }
        Task task = taskRepository.save(data);

        return successHandler.successMessageHandler("Task added successfully", List.of(taskRepository.save(task)));
    }

    @Override
    public ResponseEntity<Object> addTaskV2(Task data) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime now = LocalDateTime.parse(formattedDateTime, formatter);

        LocalDateTime dateTime1;
        LocalDateTime dateTime2;

        try {
            dateTime1 = LocalDateTime.parse(data.getAssignedDate(), formatter);
            dateTime2 = LocalDateTime.parse(data.getDue(), formatter);

        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd HH:mm:ss");
        }
        if (dateTime1.isBefore(dateTime2)) {

            data.setDue(data.getDue());
        } else if (dateTime1.isAfter(dateTime2)) {
            throw new TaskNotFoundException("Invalid : Due date prior than Assigned date.");
        } else if (dateTime1.equals(dateTime2)) {
            throw new TaskNotFoundException("Invalid : Assigned Date should not be same as Due date.");
        }

        if (dateTime1.isAfter(now)) {
            data.setDue(data.getDue());
        } else {
            throw new TaskNotFoundException("Invalid Assigned date.");
        }
        if (data.getAssignedTo() == null || data.getAssignedBy() == null || data.getTaskTitle() == null || data.getPriority() == null ||
                data.getAssignedTo().length() == 0 || data.getAssignedBy().length() == 0 || data.getTaskTitle().length() == 0
        ) {
            throw new TaskNotFoundException("Fill all the fields.");
        }
        if (!(data.getPriority()).equalsIgnoreCase("low") && !(data.getPriority()).equalsIgnoreCase("medium") && !(data.getPriority()).equalsIgnoreCase("high")) {
            throw new TaskNotFoundException("Invalid priority type.");
        }
        Task task = null;
        String message = "Task added successfully";

        if (data.getTaskTitle().length() < 255) {
            task = taskRepository.save(data);
        } else {
            message = "Task title is too long. Should be less than 255 character.";
        }
        assert task != null;
        return successHandler.successMessageHandler(message, List.of(task));
    }

    @Override
    public ResponseEntity<Object> getTaskByAssignedDateStartAfter(String assignedDate, Integer page, Integer pageSize) {
        Pageable pageable = null;
        int count = 0;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        try {
            LocalDate.parse(assignedDate, formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd");
        }

        List<Task> task = taskRepository.findByAssignedDateAfterOrderByTaskIdDesc(assignedDate + " 23:59:59", pageable);
        count = taskRepository.findByAssignedDateAfterOrderByTaskIdDesc(assignedDate + " 23:59:59").size();
        return successHandler.successMessageHandler("Task retrieved successfully", task, count);
    }

    @Override
    public ResponseEntity<Object> getTaskByAssignedDateStartAfter(String assignedDate, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        Pageable pageable = null;
        List<Task> task = new ArrayList<>();
        int count = 0;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        try {
            LocalDate.parse(assignedDate, formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd");
        }

        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }
        if (assignedBy != null && !assignedBy.equals("") && assignedTo != null && !assignedTo.equals("")) {
            task = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedDate + " 23:59:59",assignedBy, assignedTo, pageable);
            count = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedDate + " 23:59:59",assignedBy, assignedTo).size();
        } else if (assignedBy != null && !assignedBy.equals("")) {
            task = taskRepository.findByAssignedDateAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedDate + " 23:59:59", assignedBy, pageable);
            count = taskRepository.findByAssignedDateAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedDate + " 23:59:59", assignedBy).size();
        } else if (assignedTo != null && !assignedTo.equals("")) {
            task = taskRepository.findByAssignedDateAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedDate + " 23:59:59", assignedTo, pageable);
            count = taskRepository.findByAssignedDateAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedDate + " 23:59:59", assignedTo).size();
        }

        System.out.println("done in in "+ task);

        return successHandler.successMessageHandler("Task retrieved successfully", task, count);
    }

    @Override
    public ResponseEntity<Object> getTaskByAssignedDateEndAfter(String due, Integer page, Integer pageSize) {
        Pageable pageable = null;
        int count = 0;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        try {
            LocalDate.parse(due, formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd");
        }
        List<Task> task = taskRepository.findByDueAfter(due + " 23:59:59", pageable);
        count = taskRepository.findByDueAfter(due + " 23:59:59").size();
        return successHandler.successMessageHandler("Task retrieved successfully", task);
    }
    @Override
    public ResponseEntity<Object> getTaskByAssignedDateEndAfter(String due,String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        Pageable pageable = null;
        int count = 0;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        try {
            LocalDate.parse(due, formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd");
        }
        List<Task> task = taskRepository.findByDueAfter(due + " 23:59:59", pageable);
        count = taskRepository.findByDueAfter(due + " 23:59:59").size();

        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }
        if (assignedBy != null && !assignedBy.equals("") && assignedTo != null && !assignedTo.equals("")) {
            task = taskRepository.findByDueAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59",assignedBy, assignedTo, pageable);
            count = taskRepository.findByDueAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59",assignedBy, assignedTo).size();
        } else if (assignedBy != null && !assignedBy.equals("")) {
            task = taskRepository.findByDueAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59", assignedBy, pageable);
            count = taskRepository.findByDueAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59", assignedBy).size();
        } else if (assignedTo != null && !assignedTo.equals("")) {
            task = taskRepository.findByDueAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59", assignedTo, pageable);
            count = taskRepository.findByDueAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59", assignedTo).size();
        }

        return successHandler.successMessageHandler("Task retrieved successfully", task,count);
    }

    @Override
    public ResponseEntity<Object> getTaskWithPrefix(String prefix, Integer page, Integer pageSize) {
        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;
        if (prefix != null && prefix.equals(" ")) {
            List<Task> allTask = taskRepository.findAllByOrderByTaskIdDesc(pageable);
            count = taskRepository.findAllByOrderByTaskIdDesc().size();
            return successHandler.successMessageHandler("Task retrieved successfully", allTask);
        }
        List<Task> task = taskRepository.findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(prefix, pageable);
        count = taskRepository.findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(prefix).size();
        return successHandler.successMessageHandler("Task retrieved successfully", task, count);
    }


    @Override
    public ResponseEntity<Object> getTask(String userName, Integer page, Integer pageSize) {
        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;
        List<Task> task = taskRepository.findByAssignedByIgnoreCaseOrAssignedToIgnoreCaseOrderByTaskIdDesc(userName, userName, pageable).size() > 0 ? taskRepository.findByAssignedByIgnoreCaseOrAssignedToIgnoreCaseOrderByTaskIdDesc(userName, userName, pageable) : null;
        count = taskRepository.findByAssignedByIgnoreCaseOrAssignedToIgnoreCaseOrderByTaskIdDesc(userName, userName).size();
        return successHandler.successMessageHandler("Task retrieved successfully", task, count);
    }

    @Override
    public ResponseEntity<Object> getTask(Integer page, Integer pageSize) {
        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;
        List<Task> task = taskRepository.findAllByOrderByTaskIdDesc(pageable).size() > 0 ? taskRepository.findAllByOrderByTaskIdDesc(pageable) : null;
        count = taskRepository.findAllByOrderByTaskIdDesc().size();
        return successHandler.successMessageHandler("Task retrieved successfully", task, count);
    }

    @Override
    public ResponseEntity<Object> getTaskById(Long id) {
        List<Task> task = taskRepository.findById(id).isPresent() ? List.of(taskRepository.findById(id).get()) : null;
        return successHandler.successMessageHandler("Task retrieved successfully", task);
    }


    @Override
    public ResponseEntity<Object> getTaskByAssignedDate(String assignedDates, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        List<Task> tasks = new ArrayList<>();

        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;

        String startingDate = "";
        String endingDate = "";

        if (assignedDates!= null && !assignedDates.isEmpty()) {
            try {
                LocalDate.parse(assignedDates, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid date. Expected format: yyyy-MM-dd");
            }
            startingDate = assignedDates + " 00:00:00";
            endingDate = assignedDates + " 23:59:59";
        }

        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }


        System.out.println("assignedDates "+assignedDates);
        System.out.println("assignedBy "+assignedBy);
        System.out.println("assignedTo "+assignedTo);

        if (!startingDate.isEmpty() && assignedBy != null && assignedTo != null && !assignedBy.equals("") && !assignedTo.equals("")) {
            tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo, pageable);
            System.out.println("1");
            count = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo).size();
        } else if (!startingDate.isEmpty() && assignedBy != null && !assignedBy.isEmpty()) {
            tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, pageable);
            System.out.println("2");

            count = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy).size();
        } else if (!startingDate.isEmpty() && assignedTo != null && !assignedTo.equals("")) {
            count = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo).size();
            System.out.println("3");
            tasks = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo, pageable);
        } else if (assignedTo != null && !assignedTo.equals("") && assignedBy != null && !assignedBy.equals("")) {
            count = taskRepository.findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedBy, assignedTo).size();
            System.out.println("4");
            tasks = taskRepository.findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedBy, assignedTo, pageable);
        } else if (!startingDate.isEmpty()) {
            tasks = taskRepository.findByAssignedDateBetweenOrderByTaskIdDesc(startingDate, endingDate, pageable);
            System.out.println("5");
            count = taskRepository.findByAssignedDateBetweenOrderByTaskIdDesc(startingDate, endingDate).size();
        } else if (assignedBy != null && !assignedBy.equals("")) {
            tasks = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy, pageable);
            System.out.println("6");
            count = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy).size();
        } else if (assignedTo != null && !assignedTo.equals("")) {
            tasks = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo, pageable);
            System.out.println("7");
            count = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo).size();
        } else {
            tasks = taskRepository.findAllByOrderByTaskIdDesc(pageable);
            System.out.println("8");
            count = taskRepository.findAllByOrderByTaskIdDesc().size();
        }

//        if(assignedTo !=null && !assignedTo.equals("") ){
//            if( assignedDates!= null && !assignedDates.isEmpty() && assignedBy!=null  && !assignedBy.equals("")){
//                tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo, pageable);
//                count = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo).size();
//
//            }else if( assignedDates!= null && !assignedDates.isEmpty() ){
//                tasks = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo, pageable);
//                count = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo).size();
//
//            }else if( assignedBy!=null  && !assignedBy.equals("")){
//                LocalDate today = LocalDate.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                String formattedDate = today.format(formatter);
//                tasks = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc( formattedDate+" 23:59:59", assignedBy,assignedTo, pageable);
//                count = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(formattedDate+" 23:59:59", assignedBy,assignedTo).size();
//            }
//        }else if(assignedBy !=null && !assignedBy.equals("")){
//            if( assignedDates!= null && !assignedDates.isEmpty() ){
//                tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy, pageable);
//                count = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy).size();
//            }
//        }
        return successHandler.successMessageHandler("Tasks retrieved successfully.", tasks, count);
    }



    @Override
    public ResponseEntity<Object> getTaskByAssignedDateV2(String assignedDates, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        List<Task> tasks = new ArrayList<>();

        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;

        String startingDate = "";
        String endingDate = "";

        if (assignedDates!= null && !assignedDates.isEmpty()) {
            try {
                LocalDate.parse(assignedDates, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid date. Expected format: yyyy-MM-dd");
            }
            startingDate = assignedDates + " 00:00:00";
            endingDate = assignedDates + " 23:59:59";
        }

        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }

//        if (!startingDate.isEmpty() && assignedBy != null && assignedTo != null && !assignedBy.equals("") && !assignedTo.equals("")) {
//            tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo, pageable);
//            System.out.println("working here "+tasks);
//            count = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo).size();
//        } else if (!startingDate.isEmpty() && assignedBy != null && !assignedBy.isEmpty()) {
//            tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, pageable);
//            count = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy).size();
//        } else if (!startingDate.isEmpty() && assignedTo != null && !assignedTo.equals("")) {
//            count = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo).size();
//            tasks = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo, pageable);
//        } else if (!startingDate.isEmpty() && assignedBy != null && !assignedBy.equals("")) {
//            count = taskRepository.findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedBy, assignedTo).size();
//            tasks = taskRepository.findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedBy, assignedTo, pageable);
//        } else if (!startingDate.isEmpty()) {
//            tasks = taskRepository.findByAssignedDateBetweenOrderByTaskIdDesc(startingDate, endingDate, pageable);
//            count = taskRepository.findByAssignedDateBetweenOrderByTaskIdDesc(startingDate, endingDate).size();
//        } else if (assignedBy != null && !assignedBy.equals("")) {
//            tasks = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy, pageable);
//            count = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy).size();
//        } else if (assignedTo != null && !assignedTo.equals("")) {
//            tasks = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo, pageable);
//            count = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo).size();
//        } else {
//            tasks = taskRepository.findAllByOrderByTaskIdDesc(pageable);
//            count = taskRepository.findAllByOrderByTaskIdDesc().size();
//        }

        if(assignedTo !=null && !assignedTo.equals("") ){
            if( assignedDates!= null && !assignedDates.isEmpty() && assignedBy!=null  && !assignedBy.equals("")){
                tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo, pageable);
                count = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo).size();

            }else if( assignedDates!= null && !assignedDates.isEmpty() ){
                tasks = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo, pageable);
                count = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo).size();

            }else if( assignedBy!=null  && !assignedBy.equals("")){
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = today.format(formatter);
                tasks = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc( formattedDate+" 23:59:59", assignedBy,assignedTo, pageable);
                count = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(formattedDate+" 23:59:59", assignedBy,assignedTo).size();
            }
        }else if(assignedBy !=null && !assignedBy.equals("")){
            if( assignedDates!= null && !assignedDates.isEmpty() ){
                tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy, pageable);
                count = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy).size();
            }
        }
        return successHandler.successMessageHandler("Tasks retrieved successfully.", tasks, count);
    }

    @Override
    public ResponseEntity<Object> getTaskByDue(String deadline, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        List<Task> tasks=null;
        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;
        String startingDate = "";
        String endingDate = "";

        if (!deadline.isEmpty()) {

            try {
                LocalDate.parse(deadline, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid date. Expected format: yyyy-MM-dd");
            }
            startingDate = deadline + " 00:00:00";
            endingDate = deadline + " 23:59:59";

        }

        if (assignedBy != null && assignedTo != null && !(assignedBy instanceof String) && !(assignedTo instanceof String)) {
            throw new TaskNotFoundException("Invalid Name.");
        }
        if (!deadline.isEmpty() && assignedBy != null && assignedTo != null && !assignedBy.equals("") && !assignedTo.equals("")) {
            tasks = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo, pageable);
            count = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo).size();
        } else if (!deadline.isEmpty() && assignedBy != null && !assignedBy.equals("")) {
            tasks = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, pageable);
            count = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy).size();
        } else if (!deadline.isEmpty() && assignedTo != null && !assignedTo.equals("")) {
            tasks = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo, pageable);
            count = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo).size();
        } else if (!deadline.isEmpty()) {
            tasks = taskRepository.findByDueBetweenOrderByTaskIdDesc(startingDate, endingDate, pageable);
            count = taskRepository.findByDueBetweenOrderByTaskIdDesc(startingDate, endingDate).size();
        } else if (assignedBy != null && !assignedBy.equals("")) {
            tasks = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy, pageable);
            count = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy).size();
        } else if (assignedTo != null && !assignedTo.equals("")) {
            tasks = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo, pageable);
            count = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo).size();
        } else {
            tasks = taskRepository.findAllByOrderByTaskIdDesc(pageable);
            count = taskRepository.findAllByOrderByTaskIdDesc().size();
        }

//        if(assignedTo !=null && !assignedTo.equals("") ){
//            if( deadline!= null && !deadline.isEmpty() && assignedBy!=null  && !assignedBy.equals("")){
//                tasks = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo, pageable);
//                count = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo).size();
//
//            }else if( deadline!= null && !deadline.isEmpty() ){
//                tasks = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo, pageable);
//                count = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo).size();
//
//            }else if( assignedBy!=null  && !assignedBy.equals("")){
//                LocalDate today = LocalDate.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                String formattedDate = today.format(formatter);
//                tasks = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc( formattedDate+" 23:59:59", assignedBy,assignedTo, pageable);
//                count = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(formattedDate+" 23:59:59", assignedBy,assignedTo).size();
//            }
//        }else if(assignedBy !=null && !assignedBy.equals("")){
//            if( deadline!= null && !deadline.isEmpty() ){
//                tasks = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy, pageable);
//                count = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy).size();
//            }
//        }
        return successHandler.successMessageHandler("Tasks retrieved successfully.", tasks, count);
    }


    @Override
    public ResponseEntity<Object> getTaskByDueV2(String deadline, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        List<Task> tasks=null;
        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize);
        }
        int count = 0;
        String startingDate = "";
        String endingDate = "";

        if (!deadline.isEmpty()) {

            try {
                LocalDate.parse(deadline, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid date. Expected format: yyyy-MM-dd");
            }
            startingDate = deadline + " 00:00:00";
            endingDate = deadline + " 23:59:59";

        }

//        if (assigned_by != null && assigned_to != null && !(assigned_by instanceof String) && !(assigned_to instanceof String)) {
//            throw new TaskNotFoundException("Invalid Name.");
//        }
//        if (!modifiedDatesStart.isEmpty() && assigned_by != null && assigned_to != null && !assigned_by.equals("") && !assigned_to.equals("")) {
//            tasks = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_by, assigned_to, pageable);
//            count = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_by, assigned_to).size();
//        } else if (!modifiedDatesStart.isEmpty() && assigned_by != null && !assigned_by.equals("")) {
//            tasks = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_by, pageable);
//            count = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_by).size();
//        } else if (!modifiedDatesStart.isEmpty() && assigned_to != null && !assigned_to.equals("")) {
//            tasks = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_to, pageable);
//            count = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_to).size();
//        } else if (!modifiedDatesStart.isEmpty()) {
//            tasks = taskRepository.findByDueBetweenOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, pageable);
//            count = taskRepository.findByDueBetweenOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd).size();
//        } else if (assigned_by != null && !assigned_by.equals("")) {
//            tasks = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assigned_by, pageable);
//            count = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assigned_by).size();
//        } else if (assigned_to != null && !assigned_to.equals("")) {
//            tasks = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assigned_to, pageable);
//            count = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assigned_to).size();
//        } else {
//            tasks = taskRepository.findAllByOrderByTaskIdDesc(pageable);
//            count = taskRepository.findAllByOrderByTaskIdDesc().size();
//        }

        if(assignedTo !=null && !assignedTo.equals("") ){
            if( deadline!= null && !deadline.isEmpty() && assignedBy!=null  && !assignedBy.equals("")){
                tasks = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo, pageable);
                count = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy,assignedTo).size();

            }else if( deadline!= null && !deadline.isEmpty() ){
                tasks = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo, pageable);
                count = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedTo).size();

            }else if( assignedBy!=null  && !assignedBy.equals("")){
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = today.format(formatter);
                tasks = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc( formattedDate+" 23:59:59", assignedBy,assignedTo, pageable);
                count = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(formattedDate+" 23:59:59", assignedBy,assignedTo).size();
            }
        }else if(assignedBy !=null && !assignedBy.equals("")){
            if( deadline!= null && !deadline.isEmpty() ){
                tasks = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy, pageable);
                count = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate,endingDate, assignedBy).size();
            }
        }
        return successHandler.successMessageHandler("Tasks retrieved successfully.", tasks, count);
    }

    @Override
    public ResponseEntity<Object> getTask(Long task_id) {
        Optional<Task> task = taskRepository.findById(task_id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with id " + task_id + " not found.");
        }
        return successHandler.successMessageHandler("Task retrieved successfully", List.of(task.get()));
    }

    @Override
    public ResponseEntity<Object> getTaskByAssignedDate(String assigned_date) {
        List<Task> tasks = taskRepository.findByAssignedDateOrderByTaskIdDesc(assigned_date);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks with starting date " + assigned_date + " not found.");
        }
        return successHandler.successMessageHandler("Task retrieved successfully", tasks);
    }

    @Override
    public ResponseEntity<Object> getTaskByDue(String deadline) {
        List<Task> tasks = taskRepository.findByDueOrderByTaskIdDesc(deadline);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks with Ending date " + deadline + " not found.");
        }
        return successHandler.successMessageHandler("Task retrieved successfully", tasks);
    }

    @Override
    public ResponseEntity<Object> getTaskByPriority(String priority) {
        List<Task> tasks = taskRepository.findByPriorityIgnoreCaseOrderByTaskIdDesc(priority);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks with priority level " + priority + " not found.");
        }
        return successHandler.successMessageHandler("Task retrieved successfully", tasks);
    }

    @Override
    public ResponseEntity<Object> getTaskByIsCompleted(boolean isCompleted) {
        List<Task> tasks = taskRepository.findByIsCompletedOrderByTaskIdDesc(isCompleted);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks with status isCompleted : " + isCompleted + " not found.");
        }
        return successHandler.successMessageHandler("Task retrieved successfully", tasks);
    }

    @Override
    public ResponseEntity<Object> deleteTaskById(Long task_id) {
        Optional<Task> task = taskRepository.findById(task_id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with id " + task_id + " not found.");
        }
        taskRepository.deleteById(task_id);
        return successHandler.successMessageHandler("Task deleted successfully.", List.of(task.get()));
    }


    @Override
    public ResponseEntity<Object> deleteTaskByAssignedDate(String assigned_date) {
        List<Task> tasks = taskRepository.findByAssignedDateOrderByTaskIdDesc(assigned_date);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task with starting date " + assigned_date + " not found.");
        }
        taskRepository.deleteAllInBatch(tasks);
        return successHandler.successMessageHandler("Tasks with starting date " + assigned_date + " deleted successfully.", tasks);
    }

    @Override
    public ResponseEntity<Object> deleteTaskByDue(String deadline) {
        List<Task> tasks = taskRepository.findByDueOrderByTaskIdDesc(deadline);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task with deadLine " + deadline + " not found.");
        }
        taskRepository.deleteAllInBatch(tasks);
        return successHandler.successMessageHandler("Tasks with deadLine " + deadline + " deleted successfully.", tasks);
    }

    @Override
    public ResponseEntity<Object> deleteTaskByPriority(String priority) {
        List<Task> tasks = taskRepository.findByPriorityIgnoreCaseOrderByTaskIdDesc(priority);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task with priority level " + priority + " not found.");
        }
        taskRepository.deleteAllInBatch(tasks);
        return successHandler.successMessageHandler("Tasks with priority level " + priority + " deleted successfully.", tasks);
    }

    @Override
    public ResponseEntity<Object> deleteTaskByIsCompleted(boolean isCompleted) {
        List<Task> tasks = taskRepository.findByIsCompletedOrderByTaskIdDesc(isCompleted);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task with status isCompleted : " + isCompleted + " not found.");
        }
        taskRepository.deleteAllInBatch(tasks);
        return successHandler.successMessageHandler("Tasks with status isCompleted : " + isCompleted + " deleted successfully.", tasks);
    }

    @Override
    public ResponseEntity<Object> updateTask(Long task_id, Task data) {
        Optional<Task> task = taskRepository.findById(task_id);

        System.out.println("assignedDate" + data.getAssignedDate());
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with id " + task_id + " not found.");
        }
        if ((data.getTaskTitle() != null) && (data.getTaskTitle().length() > 0)) {
            task.get().setTaskTitle(data.getTaskTitle());
        }
        if ((data.getAssignedBy() != null) && (data.getAssignedBy().length() > 0)) {
            task.get().setAssignedBy(data.getAssignedBy());
        }
        if ((data.getAssignedTo() != null) && (data.getAssignedTo().length() > 0)) {
            task.get().setAssignedTo(data.getAssignedTo());
        }
        if ((data.getAssignedDate() != null)
//                &&
//                (LocalDate.parse(data.getAssignedDate(), formatter).isAfter(LocalDate.now()))
        ) {
            task.get().setAssignedDate(data.getAssignedDate());
            System.out.println("comming ass");
        }
        if ((data.getDue() != null)
//                && (LocalDate.parse(data.getDue(), formatter).isAfter(LocalDate.now()))
        ) {
            task.get().setDue(data.getDue());
            System.out.println("comming due");
        }
        if ((data.getPriority() != null) && (data.getPriority().length() > 0)) {
            task.get().setPriority(data.getPriority());
        }
        try {
            Boolean isCompleted = data.getIsCompleted();
            task.get().setIsCompleted(data.getIsCompleted());
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid Status.");
        }

        System.out.println("task.get() "+task.get());

        return successHandler.successMessageHandler("Tasks updated successfully.", List.of(taskRepository.save(task.get())));
    }

    @Override
    public ResponseEntity<Object> getTaskWithCreatedAndPrefixForUpcoming(String prefix, String created, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
//        System.out.println("prefix" + prefix);
//        System.out.println("assignedDate" + created);
//        System.out.println("assignedBy" + assignedBy);
//        System.out.println("assignedTo" + assignedTo);
//        System.out.println("page" + page);
//        System.out.println("pageSize" + pageSize);
        Pageable pageable = null;
        int count = 0;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "taskId"));
        }
        try {
            LocalDate.parse(created, formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd , really");
        }

        List<Task> upcomingTask = null;
        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }
        if (assignedBy != null && !assignedBy.equals("") && assignedTo != null && !assignedTo.equals("")) {
            upcomingTask = taskRepository.findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(created + " 23:59:59",assignedBy, assignedTo);
        } else if (assignedBy != null && !assignedBy.equals("")) {
            upcomingTask = taskRepository.findByAssignedDateAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(created + " 23:59:59", assignedBy);
        } else if (assignedTo != null && !assignedTo.equals("")) {
            upcomingTask = taskRepository.findByAssignedDateAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(created + " 23:59:59", assignedTo);
        }else{
            upcomingTask = taskRepository.findByAssignedDateAfterOrderByTaskIdDesc(created + " 23:59:59");
        }


        if (prefix != null && prefix.equals(" ")) {
            return successHandler.successMessageHandler("Task retrieved successfully", upcomingTask);
        }

        List<Task> searchedTasks = taskRepository.findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(prefix);

        List<Task> commonTasks = new ArrayList<>(searchedTasks);
        commonTasks.retainAll(upcomingTask);
        count = commonTasks.size();

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min((start + pageable.getPageSize()), commonTasks.size());
        Page<Task> commonTasksPage = new PageImpl<>(commonTasks.subList(start, end), pageable, commonTasks.size());

        return successHandler.successMessageHandler("Task retrieved successfully done", commonTasksPage.getContent(), count);

    }

    @Override
    public ResponseEntity<Object> getTaskWithDueAndPrefixForUpcoming(String prefix, String due, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
//        System.out.println("prefix" + prefix);
//        System.out.println("assignedDate" + due);
//        System.out.println("assignedBy" + assignedBy);
//        System.out.println("assignedTo" + assignedTo);
//        System.out.println("page" + page);
//        System.out.println("pageSize" + pageSize);

        Pageable pageable = null;
        int count = 0;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "taskId"));
        }
        try {
            LocalDate.parse(due, formatter1);
        } catch (Exception e) {
            throw new TaskNotFoundException("Invalid date.Expected format: yyyy-MM-dd , really");
        }

        List<Task> upcomingTask = null;
        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }
        if (assignedBy != null && !assignedBy.equals("") && assignedTo != null && !assignedTo.equals("")) {
            upcomingTask = taskRepository.findByDueAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59",assignedBy, assignedTo);
        } else if (assignedBy != null && !assignedBy.equals("")) {
            upcomingTask = taskRepository.findByDueAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59", assignedBy);
        } else if (assignedTo != null && !assignedTo.equals("")) {
            upcomingTask = taskRepository.findByDueAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(due + " 23:59:59", assignedTo);
        }


        if (prefix != null && prefix.equals(" ")) {
            return successHandler.successMessageHandler("Task retrieved successfully", upcomingTask);
        }

        List<Task> searchedTasks = taskRepository.findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(prefix);

        List<Task> commonTasks = new ArrayList<>(searchedTasks);
        commonTasks.retainAll(upcomingTask);
        count = commonTasks.size();

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min((start + pageable.getPageSize()), commonTasks.size());
        Page<Task> commonTasksPage = new PageImpl<>(commonTasks.subList(start, end), pageable, commonTasks.size());

        return successHandler.successMessageHandler("Task retrieved successfully done", commonTasksPage.getContent(), count);

    }

    @Override
    public ResponseEntity<Object> getTaskByAssignedDateWithPrefix(String prefix, String assignedDates, String assignedBy, String assignedTo, Integer page, Integer pageSize) {
        List<Task> tasks = new ArrayList<>();

        Pageable pageable = null;
        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "taskId"));
        }
        int count = 0;

        String startingDate = "";
        String endingDate = "";

        if (!assignedDates.isEmpty()) {
            try {
                LocalDate.parse(assignedDates, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid date. Expected format: yyyy-MM-dd");
            }
            startingDate = assignedDates + " 00:00:00";
            endingDate = assignedDates + " 23:59:59";
        }

        if (assignedBy != null && assignedTo != null) {
            if (!(assignedBy instanceof String) || !(assignedTo instanceof String)) {
                throw new TaskNotFoundException("Invalid Name.");
            }
        }

        if (!startingDate.isEmpty() && assignedBy != null && assignedTo != null && !assignedBy.equals("") && !assignedTo.equals("")) {
            tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy, assignedTo);
        } else if (!startingDate.isEmpty() && assignedBy != null && !assignedBy.isEmpty()) {
            tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedBy);
        } else if (!startingDate.isEmpty() && assignedTo != null && !assignedTo.equals("")) {
            tasks = taskRepository.findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(startingDate, endingDate, assignedTo);
        } else if (!startingDate.isEmpty() && assignedBy != null && !assignedBy.equals("")) {
            tasks = taskRepository.findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedBy, assignedTo);
        } else if (!startingDate.isEmpty()) {
            tasks = taskRepository.findByAssignedDateBetweenOrderByTaskIdDesc(startingDate, endingDate);
        } else if (assignedBy != null && !assignedBy.equals("")) {
            tasks = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assignedBy);
        } else if (assignedTo != null && !assignedTo.equals("")) {
            tasks = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assignedTo);
        } else {
            tasks = taskRepository.findAllByOrderByTaskIdDesc();
        }
        List<Task> searchedTasks = taskRepository.findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(prefix);

        List<Task> commonTasks = new ArrayList<>(searchedTasks);
        commonTasks.retainAll(tasks);
        count = commonTasks.size();

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min((start + pageable.getPageSize()), commonTasks.size());
        Page<Task> commonTasksPage = new PageImpl<>(commonTasks.subList(start, end), pageable, commonTasks.size());
        count = commonTasksPage.getContent().size();

        return successHandler.successMessageHandler("Task retrieved successfully done", commonTasksPage.getContent(), count);
    }

    @Override
    public ResponseEntity<Object> getTaskByDueWithPrefix(String prefix, String deadline, String assigned_by, String assigned_to, Integer page, Integer pageSize) {
        List<Task> tasks;
        Pageable pageable = null;
//        if (page != null) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "taskId"));
//        }
        int count = 0;
        String modifiedDatesStart = "";
        String modifiedDatesEnd = "";

        if (deadline!= null && !deadline.isEmpty()) {

            try {
                LocalDate.parse(deadline, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid date. Expected format: yyyy-MM-dd");
            }
            modifiedDatesStart = deadline + " 00:00:00";
            modifiedDatesEnd = deadline + " 23:59:59";

        }

        if (assigned_by != null && assigned_to != null && !(assigned_by instanceof String) && !(assigned_to instanceof String)) {
            throw new TaskNotFoundException("Invalid Name.");
        }
        if (!modifiedDatesStart.isEmpty() && assigned_by != null && assigned_to != null && !assigned_by.equals("") && !assigned_to.equals("")) {
            tasks = taskRepository.findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_by, assigned_to);
        } else if (!modifiedDatesStart.isEmpty() && assigned_by != null && !assigned_by.equals("")) {
            tasks = taskRepository.findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_by);
        } else if (!modifiedDatesStart.isEmpty() && assigned_to != null && !assigned_to.equals("")) {
            tasks = taskRepository.findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd, assigned_to);
        } else if (!modifiedDatesStart.isEmpty()) {
            tasks = taskRepository.findByDueBetweenOrderByTaskIdDesc(modifiedDatesStart, modifiedDatesEnd);
        } else if (assigned_by != null && !assigned_by.equals("")) {
            tasks = taskRepository.findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(assigned_by);
        } else if (assigned_to != null && !assigned_to.equals("")) {
            tasks = taskRepository.findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(assigned_to);
        } else {
            tasks = taskRepository.findAllByOrderByTaskIdDesc();
        }
        List<Task> searchedTasks = taskRepository.findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(prefix);

        List<Task> commonTasks = new ArrayList<>(searchedTasks);
        commonTasks.retainAll(tasks);

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min((start + pageable.getPageSize()), commonTasks.size());

        if (start <= end) {
            // Create the sublist only if start is less than or equal to end
            Page<Task> commonTasksPage = new PageImpl<>(commonTasks.subList(start, end), pageable, commonTasks.size());
            count = commonTasksPage.getContent().size();
            return successHandler.successMessageHandler("Task retrieved successfully", commonTasksPage.getContent(), count);
        } else {
            // Handle the case when start is greater than end (invalid range)
            return successHandler.successMessageHandler("No tasks found", new ArrayList<>(), 0);
        }
    }

    @Override
    public ResponseEntity<Object> getTodayTaskByDue(String today, String deadline, String assignedBy,String assigned_to, Integer page, Integer pageSize) {
        List<Task> tasks = null;
        Pageable pageable = null;
        pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "taskId"));
        int count = 0;
        String modifiedDatesStart = "";
        String modifiedDatesEnd = "";
        String modifiedDeadlineStart = "";
        String modifiedDeadlineEnd = "";

        if (today!= null && !today.isEmpty()) {
            try {
                LocalDate.parse(today, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid Assigned Date. Expected format: yyyy-MM-dd");
            }
            modifiedDatesStart = today + " 00:00:00";
            modifiedDatesEnd = today + " 23:59:59";
        }
        if (deadline!= null && !deadline.isEmpty()) {
            try {
                LocalDate.parse(deadline, formatter1);
            } catch (DateTimeParseException e) {
                throw new TaskNotFoundException("Invalid Deadline. Expected format: yyyy-MM-dd");
            }
            modifiedDeadlineStart= deadline + " 00:00:00";
            modifiedDeadlineEnd = deadline + " 23:59:59";
        }

        if(assigned_to !=null && !assigned_to.equals("") && today!= null && !today.isEmpty()){
            if( deadline!= null && !deadline.isEmpty() && assignedBy!=null  && !assignedBy.equals("")){
                tasks = taskRepository.findByAssignedDateBetweenAndDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, modifiedDeadlineStart,modifiedDeadlineEnd, assignedBy,assigned_to, pageable);
                count = taskRepository.findByAssignedDateBetweenAndDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, modifiedDeadlineStart,modifiedDeadlineEnd, assignedBy,assigned_to).size();

            }else if(assignedBy!=null  && !assignedBy.equals("")){
                tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, assignedBy,assigned_to, pageable);
                count = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, assignedBy,assigned_to).size();

            }else if( deadline!= null && !deadline.isEmpty()){
                tasks = taskRepository.findByAssignedDateBetweenAndDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, modifiedDeadlineStart,modifiedDeadlineEnd,assigned_to, pageable);
                count = taskRepository.findByAssignedDateBetweenAndDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, modifiedDeadlineStart,modifiedDeadlineEnd,assigned_to).size();

            }

        }else if(assignedBy !=null && !assignedBy.equals("") && today!= null && !today.isEmpty()){
            if(assigned_to!=null  && !assigned_to.equals("")){
                tasks = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, assignedBy,assigned_to, pageable);
                count = taskRepository.findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, assignedBy,assigned_to).size();
            }else if( deadline!= null && !deadline.isEmpty()){
                tasks = taskRepository.findByAssignedDateBetweenAndDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd, modifiedDeadlineStart,modifiedDeadlineEnd,assignedBy, pageable);
                count = taskRepository.findByAssignedDateBetweenAndDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(modifiedDatesStart,modifiedDatesEnd,modifiedDeadlineStart,modifiedDeadlineEnd,assignedBy).size();
            }
        }
        System.out.println("!!!!! "+tasks);

        return successHandler.successMessageHandler("Task retrieved successfully", tasks, count);
    }


}


