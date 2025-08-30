package org.tracker.repository;

import org.tracker.model.Task;

import java.util.List;

public interface TaskRepository {

    void save(Task task);

    Task findById(Long id);

    List<Task> findAll();

    Task update(Task task);

    void deleteById(Long id);
}
