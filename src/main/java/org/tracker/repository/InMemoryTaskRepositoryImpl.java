package org.tracker.repository;

import org.springframework.stereotype.Repository;
import org.tracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTaskRepositoryImpl implements TaskRepository {

    private long idCounter = 1L;
    private Map<Long, Task> taskMap = new HashMap<>();

    @Override
    public void save(Task task) {
        task.setId(countId());
        taskMap.put(task.getId(), task);
    }

    @Override
    public Task findById(Long id) {
        return taskMap.get(id);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public Task update(Task task) {
        taskMap.put(task.getId(), task);
        return task;
    }

    @Override
    public void deleteById(Long id) {
        taskMap.remove(id);
    }

    private long countId() {
        return idCounter++;
    }
}
