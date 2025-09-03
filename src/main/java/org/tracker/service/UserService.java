package org.tracker.service;

import org.tracker.dto.task.TaskDto;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;
import org.tracker.model.Task;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<TaskDto> getTasksForUser(Long userId);

    void createUser(UserCreateDto dto);
    Optional<UserDto> findById(Long id);
    List<UserDto> findAll();
    void deleteById(Long id);
}
