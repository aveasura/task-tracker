package org.tracker.service;

import org.tracker.dto.task.TaskDto;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<TaskDto> getTasksForUserByUserId(Long id);

    UserDto createUser(UserCreateDto dto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void deleteUserById(Long id);
}
