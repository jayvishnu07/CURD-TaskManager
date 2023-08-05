package com.backend.Repository;

import com.backend.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByAssignedDateOrderByTaskIdDesc(String assigned_date);
    List<Task> findByDueOrderByTaskIdDesc(String deadline);
    List<Task> findByPriorityIgnoreCaseOrderByTaskIdDesc(String priority);
    List<Task> findByIsCompletedOrderByTaskIdDesc(boolean isCompleted);
    List<Task> findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(String assignedBy ,Pageable pageable);
    List<Task> findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String deadlineStart, String deadlineEnd, String assignedBy, String assignedTo,Pageable pageable);
    List<Task> findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String deadlineStart, String deadlineEnd, String assignedBy,Pageable pageable);

    List<Task> findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String deadlineStart, String deadlineEnd, String assignedTo, Pageable pageable);

    List<Task> findByDueBetweenOrderByTaskIdDesc(String assignedBy, String assignedTo, Pageable pageable);

    List<Task> findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedTo,Pageable pageable);

    List<Task> findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String startingDate, String endingDate, String assignedBy, String assignedTo, Pageable pageable);

    List<Task> findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String startingDate, String endingDate, String assignedBy, Pageable pageable);

    List<Task> findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedBy, String assignedTo,Pageable pageable);

    List<Task> findByAssignedDateAfterOrderByTaskIdDesc(String assignedDate, Pageable pageable);
    List<Task> findByAssignedDateAfterOrderByTaskIdDesc(String assignedDate);
    List<Task> findByDueAfter(String due, Pageable pageable);
    List<Task> findByDueAfter(String due);
    List<Task> findByAssignedDateBetweenOrderByTaskIdDesc(String startingDate, String endingDate,Pageable pageable);
    List<Task> findAllByOrderByTaskIdDesc(Pageable pageable);
    List<Task> findAllByOrderByTaskIdDesc();
    List<Task> findByAssignedByAllIgnoreCaseOrderByTaskIdDesc(String assignedBy );
    List<Task> findByDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String deadlineStart, String deadlineEnd, String assignedBy, String assignedTo);
    List<Task> findByDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String deadlineStart, String deadlineEnd, String assignedBy);

    List<Task> findByDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String deadlineStart, String deadlineEnd, String assignedTo);

    List<Task> findByDueBetweenOrderByTaskIdDesc(String assignedBy, String assignedTo);

    List<Task> findByAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedTo);

    List<Task> findByAssignedDateBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String startingDate, String endingDate, String assignedBy, String assignedTo);

    List<Task> findByAssignedDateBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String startingDate, String endingDate, String assignedBy);

    List<Task> findByAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedBy, String assignedTo);

    List<Task> findByAssignedDateBetweenOrderByTaskIdDesc(String startingDate, String endingDate);


    List<Task> findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(String prefix,Pageable pageable);
    List<Task> findByTaskTitleIgnoreCaseStartingWithOrderByTaskIdDesc(String prefix);

//    after authentication

    List<Task> findByAssignedByIgnoreCaseOrAssignedToIgnoreCaseOrderByTaskIdDesc(String assignedBy,String assignedTo,Pageable pageable);
    List<Task> findByAssignedByIgnoreCaseOrAssignedToIgnoreCaseOrderByTaskIdDesc(String assignedBy,String assignedTo);

    List<Task> findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String startingDate, String endingDate, String assignedTo, Pageable pageable);
    List<Task> findByAssignedDateBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String startingDate, String endingDate, String assignedTo);

    List<Task> findByAssignedDateAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String assignedDate, String assignedTo);
    List<Task> findByAssignedDateAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String assignedDate, String assignedTo,Pageable pageable);

    List<Task> findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedDate, String assignedBy, String assignedTo, Pageable pageable);
    List<Task> findByAssignedDateAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedDate, String assignedBy, String assignedTo);

    List<Task> findByAssignedDateAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedDate, String assignedTo, Pageable pageable);
    List<Task> findByAssignedDateAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String assignedDate, String assignedTo);

    List<Task> findByDueAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String due, String assignedTo);
    List<Task> findByDueAfterAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String due, String assignedTo, Pageable pageable);

    List<Task> findByDueAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String due, String assignedBy, String assignedTo);
    List<Task> findByDueAfterAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String due, String assignedBy, String assignedTo, Pageable pageable);

    List<Task> findByDueAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String due, String assignedBy);
    List<Task> findByDueAfterAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String due, String assignedBy, Pageable pageable);

    Page<Task> findByAssignedDateAfter(String s, Pageable pageable);

    List<Task> findByAssignedDateBetweenAndDueAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, String assignedBy,String assigned_to, Pageable pageable);
    List<Task> findByAssignedDateBetweenAndDueAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, String assignedBy,String assigned_to);

    List<Task> findByAssignedDateBetweenAndAssignedByOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, Pageable pageable);
    List<Task> findByAssignedDateBetweenAndAssignedByOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline);

    List<Task> findByAssignedDateBetweenAndDueOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, Pageable pageable);
    List<Task> findByAssignedDateBetweenAndDueOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline);

    List<Task> findByAssignedDateBetweenAndDueAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, String assignedTo, Pageable pageable);
    List<Task> findByAssignedDateBetweenAndDueAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, String assignedTo);

    List<Task> findByAssignedDateBetweenAndDueAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, String assignedBy, Pageable pageable);
    List<Task> findByAssignedDateBetweenAndDueAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String deadline, String assignedBy);

    List<Task> findByAssignedDateBetweenAndDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String modifiedDeadlineStart, String modifiedDeadlineEnd, String assignedBy, String assignedTo);
    List<Task> findByAssignedDateBetweenAndDueBetweenAndAssignedByAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String modifiedDeadlineStart, String modifiedDeadlineEnd, String assignedBy, String assignedTo, Pageable pageable);

    List<Task> findByAssignedDateBetweenAndDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String modifiedDeadlineStart, String modifiedDeadlineEnd, String assignedTo);
    List<Task> findByAssignedDateBetweenAndDueBetweenAndAssignedToAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String modifiedDeadlineStart, String modifiedDeadlineEnd, String assignedTo, Pageable pageable);

    List<Task> findByAssignedDateBetweenAndDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String modifiedDeadlineStart, String modifiedDeadlineEnd, String assignedBy);
    List<Task> findByAssignedDateBetweenAndDueBetweenAndAssignedByAllIgnoreCaseOrderByTaskIdDesc(String modifiedDatesStart, String modifiedDatesEnd, String modifiedDeadlineStart, String modifiedDeadlineEnd, String assignedBy, Pageable pageable);
}




