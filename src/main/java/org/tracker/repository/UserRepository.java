package org.tracker.repository;

import org.tracker.model.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findById(Long id);

    List<User> findAll();

    User update(User user);

    void deleteById(Long id);

}
