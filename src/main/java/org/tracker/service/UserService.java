package org.tracker.service;

import org.tracker.model.Task;
import org.tracker.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Task> getTasksForUser(Long userId);

    void save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}
