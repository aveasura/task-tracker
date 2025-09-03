package org.tracker.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tracker.dto.task.TaskDto;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;
import org.tracker.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto dto) {
        UserDto created = userService.createUser(dto);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // получить таски для юзера по его id
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTasksForUserByUserId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteUserById(id); // кинет 404, если нет
        return ResponseEntity.noContent().build(); // 204
    }
}
