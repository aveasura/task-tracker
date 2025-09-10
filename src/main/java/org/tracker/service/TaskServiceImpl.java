package org.tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.exception.ResourceNotFoundException;
import org.tracker.mapper.TaskMapper;
import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    public TaskDto assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task с id=" + taskId + " не найден"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User с id=" + userId + " не найден"));

        task.setAssignee(user);
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Override
    @Transactional
    public TaskDto changeTaskStatus(Long taskId, String newStatus) {
        Status parsed = Arrays.stream(Status.values())
                .filter(s -> s.name().equalsIgnoreCase(newStatus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неверный статус: " + newStatus));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task с id=" + taskId + " не найден"));

        task.setStatus(parsed);
        return TaskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getTasks(Priority priority, Status status) {
        List<Task> tasks;
        if (priority != null && status != null) {
            tasks = taskRepository.findByPriorityAndStatus(priority, status);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public List<TaskDto> getOverdueTasks() {
        return taskRepository.findByDueDateBefore(LocalDateTime.now()).stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public List<TaskDto> getUnassignedTasks() {
        return taskRepository.findByAssigneeIsNull().stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public TaskDto createTask(TaskCreateDto dto) {
        Task task = TaskMapper.toEntity(dto);
        Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Task с id=" + id + " не найден"));
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task с id=" + id + " не найден"));

        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public void unassignTask(Long id) {
        unassignInternal(id);
        // ничего не возвращаем, контроллер отдаст 204 ноу контент
    }

    @Override
    @Transactional
    public TaskDto unassignTaskAndReturn(Long id) {
        Task task = unassignInternal(id);
        return TaskMapper.toDto(task);
    }

    private Task unassignInternal(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: id=" + id));

        if (task.getAssignee() != null) {
            task.setAssignee(null); // jpa dirty checking сохранит на коммите
        }

        return task;
    }
}
