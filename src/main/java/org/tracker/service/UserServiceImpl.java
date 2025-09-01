package org.tracker.service;

import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTasksForUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("Пользователя с таким id не найдено");
        }

        List<Task> userTasks = taskRepository.findAll().stream()
                .filter(task -> task.getAssignee() != null && task.getAssignee().getId().equals(userId))
                .toList();
        return userTasks.isEmpty() ? List.of() : userTasks;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users == null ? List.of() : users;
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NoSuchElementException("Пользователя с id=" + id + " не найдено");
        }

        taskRepository.findAll().stream().filter(task -> task.getAssignee() != null &&  task.getAssignee().getId().equals(id))
                .forEach(task -> {
                    task.setAssignee(null);
                    taskRepository.update(task);
                });

        userRepository.deleteById(id);
    }
}
