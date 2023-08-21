package com.backend.Controller;

import com.backend.Entity.Task;
import com.backend.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/")
public class TaskController {
    @Autowired
    private TaskService taskService;

    //POST REQUESTS

    //VERSION - 1
//    @PreAuthorize("hasRole('user') or T(java.lang.String).equalsIgnoreCase(task.name, jwtUtil.getGivenNameFromJwt())")
    @PostMapping("v1/tasks")
    @Secured("ROLE_user")
    public ResponseEntity<Object> addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    //VERSION - 2
    @PostMapping("v2/tasks")
    @Secured("ROLE_user")
    public ResponseEntity<Object> addTaskV2(@RequestBody Task task) {
        return taskService.addTaskV2(task);
    }


    //GET REQUESTS

    //VERSION - 1
    @GetMapping("v1/tasks")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTask(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        return taskService.getTask(page, pageSize);
    }

    //VERSION - 2 [ GET ALL TASK RELATED TO THE USER (userName) ]
    @GetMapping("v2/tasks/{userName}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTask(@PathVariable("userName") String userName,
                                          @RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return taskService.getTask(userName, page, pageSize);
    }

    //VERSION - 1 [ GET ALL TASK BY ID (id) ]
    @GetMapping("v1/tasks/id/{id}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskById(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    //    VERSION - 1 [ GET ALL TASK BY TITLE (prefix) ]
    @GetMapping("v1/tasks/title/{prefix}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskWithPrefix(@PathVariable String prefix,
                                                    @RequestParam(value = "page", required = false) @Valid Integer page,
                                                    @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize) {
        return taskService.getTaskWithPrefix(prefix, page, pageSize);
    }

    //VERSION - 1 [ GET ALL TASK BY ASSIGNED DATE ]
    @GetMapping("v1/tasks/created")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByAssignedDate(@RequestParam(value = "created", required = false) @Valid String assigned_date,
                                                        @RequestParam(value = "by", required = false) @Valid String assigned_by,
                                                        @RequestParam(value = "to", required = false) @Valid String assigned_to,
                                                        @RequestParam(value = "page", required = false) @Valid Integer page,
                                                        @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize
    ) {
        return taskService.getTaskByAssignedDate(assigned_date, assigned_by, assigned_to, page, pageSize);
    }

    //VERSION - 1 [ GET ALL TASK BY ASSIGNED DATE ]
    @GetMapping("v2/tasks/created")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByAssignedDateV2(@RequestParam(value = "created", required = false) @Valid String assigned_date,
                                                          @RequestParam(value = "by", required = false) @Valid String assigned_by,
                                                          @RequestParam(value = "to", required = false) @Valid String assigned_to,
                                                          @RequestParam(value = "page", required = false) @Valid Integer page,
                                                          @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize
    ) {
        System.out.println("assigned_date" + assigned_date);
        return taskService.getTaskByAssignedDateV2(assigned_date, assigned_by, assigned_to, page, pageSize);
    }

    @GetMapping("v2/tasks/created/{prefix}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByAssignedDateWithPrefix(@PathVariable String prefix,
                                                                  @RequestParam(value = "created", required = false) @Valid String assigned_date,
                                                                  @RequestParam(value = "by", required = false) @Valid String assigned_by,
                                                                  @RequestParam(value = "to", required = false) @Valid String assigned_to,
                                                                  @RequestParam(value = "page", required = false) @Valid Integer page,
                                                                  @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize
    ) {
        return taskService.getTaskByAssignedDateWithPrefix(prefix, assigned_date, assigned_by, assigned_to, page, pageSize);
    }

    @GetMapping("v1/tasks/due")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByDue(@RequestParam(value = "due", required = false) String deadline,
                                               @RequestParam(value = "by", required = false) String assigned_by,
                                               @RequestParam(value = "to", required = false) String assigned_to,
                                               @RequestParam(value = "page", required = false) @Valid Integer page,
                                               @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize
    ) {
        return taskService.getTaskByDue(deadline, assigned_by, assigned_to, page, pageSize);
    }

    @GetMapping("v2/tasks/due")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByDueV2(@RequestParam(value = "due", required = false) String deadline,
                                                 @RequestParam(value = "by", required = false) String assigned_by,
                                                 @RequestParam(value = "to", required = false) String assigned_to,
                                                 @RequestParam(value = "page", required = false) @Valid Integer page,
                                                 @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize
    ) {
        return taskService.getTaskByDueV2(deadline, assigned_by, assigned_to, page, pageSize);
    }


    @GetMapping("v2/tasks/due/{prefix}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByDueWithPrefix(@PathVariable String prefix,
                                                         @RequestParam(value = "due", required = false) String deadline,
                                                         @RequestParam(value = "by", required = false) String assigned_by,
                                                         @RequestParam(value = "to", required = false) String assigned_to,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") @Valid Integer page,
                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Valid Integer pageSize
    ) {
        return taskService.getTaskByDueWithPrefix(prefix, deadline, assigned_by, assigned_to, page, pageSize);
    }

    @GetMapping("v2/tasks/after/{assigned_date}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskByAssignedDateStartAfter(@PathVariable String assigned_date,
                                                                  @RequestParam(value = "by", required = false) String assignedBy,
                                                                  @RequestParam(value = "to", required = false) String assignedTo,
                                                                  @RequestParam(value = "page", required = false) @Valid Integer page,
                                                                  @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize) {
        return taskService.getTaskByAssignedDateStartAfter(assigned_date, assignedBy, assignedTo, page, pageSize);
    }

    @GetMapping("/v1/tasksId/{taskId}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }


    @DeleteMapping("/v1/tasks/id/{taskId}")
    @Secured("ROLE_admin")
    @ResponseBody
    public ResponseEntity<Object> deleteTaskById(@PathVariable Long taskId) {
        return taskService.deleteTaskById(taskId);
    }

    @PutMapping("/v1/tasks/id/{taskId}")
    @Secured("ROLE_user")
    @ResponseBody
    public ResponseEntity<Object> updateTask(@PathVariable Long taskId, @RequestBody Task data) {
        return taskService.updateTask(taskId, data);
    }

    @GetMapping("v1/tasks/upcoming/{prefix}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskWithCreatedAndPrefixForUpcoming(@PathVariable String prefix,
                                                                         @RequestParam(value = "created", required = false) @Valid String created,
                                                                         @RequestParam(value = "by", required = false) @Valid String assignedBy,
                                                                         @RequestParam(value = "to", required = false) @Valid String assignedTo,
                                                                         @RequestParam(value = "page", required = false) @Valid Integer page,
                                                                         @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize) {
        return taskService.getTaskWithCreatedAndPrefixForUpcoming(prefix, created, assignedBy, assignedTo, page, pageSize);
    }

    @GetMapping("v1/tasks/upcoming/due/title/{prefix}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTaskWithDueAndPrefixForUpcoming(@PathVariable String prefix,
                                                                     @RequestParam(value = "due", required = false) @Valid String due,
                                                                     @RequestParam(value = "by", required = false) @Valid String assignedBy,
                                                                     @RequestParam(value = "to", required = false) @Valid String assignedTo,
                                                                     @RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return taskService.getTaskWithDueAndPrefixForUpcoming(prefix, due, assignedBy, assignedTo, page, pageSize);
    }

    @GetMapping("v1/tasks/today/{today}")
    @Secured("ROLE_user")
    public ResponseEntity<Object> getTodayTaskByDue(@PathVariable String today,
                                                    @RequestParam(value = "due", required = false) String deadline,
                                                    @RequestParam(value = "by", required = false) String assigned_by,
                                                    @RequestParam(value = "to", required = false) String assigned_to,
                                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return taskService.getTodayTaskByDue(today, deadline, assigned_by, assigned_to, page, pageSize);
    }


    //    @GetMapping("v1/tasks/starts-after/{assigned_date}")
//    @Secured("ROLE_user")
//    public ResponseEntity<Object> getTaskByAssignedDateStartAfter(@PathVariable String assigned_date,
//                                                                  @RequestParam(value = "page", required = false) @Valid Integer page,
//                                                                  @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize) {
//        return taskService.getTaskByAssignedDateStartAfter(assigned_date, page, pageSize);
//    }

//    @GetMapping("v1/tasks/ends-after/{due}")
//    @Secured("ROLE_user")
//    public ResponseEntity<Object> getTaskByAssignedDateEndAfter(@PathVariable String due,
//                                                                @RequestParam(value = "page", required = false) @Valid Integer page,
//                                                                @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize) {
//        System.out.println("due => " + due);
//        return taskService.getTaskByAssignedDateEndAfter(due, page, pageSize);
//    }

//    @GetMapping("v2/tasks/ends-after/{due}")
//    @Secured("ROLE_user")
//    public ResponseEntity<Object> getTaskByAssignedDateEndAfter(@PathVariable String due,
//                                                                @RequestParam(value = "by", required = false) String assignedBy,
//                                                                @RequestParam(value = "to", required = false) String assignedTo,
//                                                                @RequestParam(value = "page", required = false) @Valid Integer page,
//                                                                @RequestParam(value = "pageSize", required = false) @Valid Integer pageSize) {
//        return taskService.getTaskByAssignedDateEndAfter(due, assignedBy, assignedTo, page, pageSize);
//    }


}
