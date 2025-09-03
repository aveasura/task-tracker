package org.tracker.service;

import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.model.Task;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    void assignTaskToUser(Long taskId, Long userId);
    void changeStatus(Long taskId, Status newStatus);
    List<TaskDto> getOverdueTasks();
    List<TaskDto> getUnassignedTasks();
    List<TaskDto> getTasksByPriority(Priority priority);
    List<TaskDto> getTasksByStatus(Status status);

    // стандартные методы дергающие repo
    void createTask(TaskCreateDto dto);
    Optional<TaskDto> findById(Long id);
    List<TaskDto> findAll();
    void deleteById(Long id);
}
