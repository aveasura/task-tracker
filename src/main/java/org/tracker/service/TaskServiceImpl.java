package org.tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.mapper.TaskMapper;
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
    public List<TaskDto> getOverdueTasks() {
        return taskRepository.findByDueDateBefore(LocalDateTime.now()).stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public List<TaskDto> getUnassignedTasks() {
        return taskRepository.findByAssigneeIsNull().stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public List<TaskDto> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority).stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public List<TaskDto> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status).stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public void createTask(TaskCreateDto dto) {
        Task task = TaskMapper.toEntity(dto);
        taskRepository.save(task);
    }

    @Override
    public Optional<TaskDto> findById(Long id) {
        return taskRepository.findById(id).map(TaskMapper::toDto);
    }

    @Override
    public List<TaskDto> findAll() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Задачи с id=" + id + " не найдено"));
        taskRepository.delete(task);
    }
}
