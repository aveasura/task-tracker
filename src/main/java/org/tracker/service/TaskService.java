package org.tracker.service;

import org.tracker.model.Task;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;

import java.util.List;

public interface TaskService {

    void assignTaskToUser(Long taskId, Long userId);
    void changeStatus(Long taskId, Status newStatus);
    List<Task> getOverdueTasks();
    List<Task> getUnassignedTasks();
    List<Task> getTasksByPriority(Priority priority);
    List<Task> getTasksByStatus(Status status);

    // стандартные методы дергающие repo
    void save(Task task);
    Task findById(Long id);
    List<Task> findAll();
    Task update(Task task);
    void deleteById(Long id);
}
