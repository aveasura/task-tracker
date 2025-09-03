package org.tracker.service;

import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<TaskDto> getTasks(Priority priority, Status status);
    TaskDto assignTaskToUser(Long taskId, Long userId);
    TaskDto changeTaskStatus(Long taskId, String newStatus);
    List<TaskDto> getOverdueTasks();
    List<TaskDto> getUnassignedTasks();

    TaskDto createTask(TaskCreateDto dto);
    TaskDto getTaskById(Long id);
    void deleteTaskById(Long id);
}
