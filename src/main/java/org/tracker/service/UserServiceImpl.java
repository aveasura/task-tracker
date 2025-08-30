package org.tracker.service;

import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;

import java.util.List;
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

        return taskRepository.findAll().stream()
                .filter(task -> task.getAssignee() != null && task.getAssignee().getId().equals(userId))
                .collect(Collectors.toList());
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
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
