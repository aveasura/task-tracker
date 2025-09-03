package org.tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.dto.task.TaskDto;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;
import org.tracker.mapper.TaskMapper;
import org.tracker.mapper.UserMapper;
import org.tracker.model.User;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> getTasksForUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователя с таким id не найдено"));
        return taskRepository.findByAssigneeId(userId).stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @Override
    public void createUser(UserCreateDto dto) {
        User user = UserMapper.toEntity(dto);
        userRepository.save(user);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Пользователь с id=" + id + " не найден"));

        taskRepository.findByAssigneeId(id)
                .forEach(task -> task.setAssignee(null));

        userRepository.delete(user);
    }
}
