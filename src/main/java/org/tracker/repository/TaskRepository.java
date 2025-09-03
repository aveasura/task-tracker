package org.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tracker.model.Task;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssigneeId(Long userId);
    List<Task> findByPriorityAndStatus(Priority priority, Status status);
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByAssigneeIsNull();
    List<Task> findByDueDateBefore(LocalDateTime now);
}
