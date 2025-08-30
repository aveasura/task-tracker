package org.tracker.service;

import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId);
        User user = userRepository.findById(userId);

        if (task == null || user == null) {
            System.out.println("Таска или Юзер не существует / екзепшен кинуть");
            return;
        }

        task.setAssignee(user);
        user.getTasks().add(task);

        taskRepository.update(task);
        userRepository.update(user);
    }

    @Override
    public void changeStatus(Long taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId);

        if (task == null) {
            System.out.println("Такой таски не существует / екзепшен");
            return;
        }

        task.setStatus(newStatus);
        taskRepository.update(task);
    }

    @Override
    public List<Task> getOverdueTasks() {
        List<Task> allTasks = taskRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        return allTasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getUnassignedTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getAssignee() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getPriority() != null && task.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getStatus() != null && task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
