package com.backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "task_sequence")
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "assigned_by")
    private String assignedBy;
    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "assigned_Date")
    private String assignedDate;

    @Column(name = "due")
    private String due;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private boolean isCompleted=false;

    @Transient
    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}

