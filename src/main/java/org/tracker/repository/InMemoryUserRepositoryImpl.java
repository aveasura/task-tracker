package org.tracker.repository;

import org.tracker.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserRepositoryImpl implements UserRepository {

    private long idCounter = 0L; // in memory user id counter
    private Map<Long, User> userMap = new HashMap<>(); // in mem users db

    @Override
    public void save(User user) {
        user.setId(countId());
        userMap.put(user.getId(), user);
    }

    @Override
    public User findById(Long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User update(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userMap.remove(id);
    }

    // in mem counter id
    private Long countId() {
        return idCounter++;
    }
}
