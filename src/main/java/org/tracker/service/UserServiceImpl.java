package org.tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.model.Task;
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
    public List<Task> getTasksForUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователя с таким id не найдено"));
        return taskRepository.findByAssigneeId(userId);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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
