package org.tracker.service;

import org.tracker.model.Task;
import org.tracker.model.User;

import java.util.List;

public interface UserService {

    List<Task> getTasksForUser(Long userId);

    void save(User user);
    User findById(Long id);
    List<User> findAll();
    User update(User user);
    void deleteById(Long id);
}
