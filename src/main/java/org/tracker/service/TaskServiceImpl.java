package org.tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task не найден"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User не найден"));
        task.setAssignee(user);
    }

    @Override
    @Transactional
    public void changeStatus(Long taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task с таким id не найден"));
        task.setStatus(newStatus);
    }

    @Override
    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBefore(LocalDateTime.now());
    }

    @Override
    public List<Task> getUnassignedTasks() {
        return taskRepository.findByAssigneeIsNull();
    }

    @Override
    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    @Override
    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Задачи с id=" + id + " не найдено"));
        taskRepository.delete(task);
    }
}
