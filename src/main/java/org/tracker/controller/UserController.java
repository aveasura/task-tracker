package org.tracker.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.tracker.dto.task.TaskDto;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;
import org.tracker.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto dto) {
        UserDto created = userService.createUser(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getTasksForUserByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId); // кинет 404, если нет
        return ResponseEntity.noContent().build(); // 204
    }

    // todo PATCH /api/v1/users/{id} для частичного апдейта (name/email).
    // todo к GET /users и GET /users/{id}/tasks прикрутить пагинацию
}
