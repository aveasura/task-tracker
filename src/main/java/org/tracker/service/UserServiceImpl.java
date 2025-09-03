package org.tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.dto.task.TaskDto;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;
import org.tracker.exception.ResourceNotFoundException;
import org.tracker.mapper.TaskMapper;
import org.tracker.mapper.UserMapper;
import org.tracker.model.User;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> getTasksForUserByUserId(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id=" + id + " не найден"));

        return taskRepository.findByAssigneeId(id).stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @Override
    public UserDto createUser(UserCreateDto dto) {
        User user = UserMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id=" + id + " не найден"));

        taskRepository.findByAssigneeId(id)
                .forEach(task -> task.setAssignee(null));

        userRepository.delete(user);
    }
}
