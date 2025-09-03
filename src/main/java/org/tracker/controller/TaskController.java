package org.tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody TaskCreateDto dto) {
        TaskDto created = taskService.createTask(dto);
        return ResponseEntity
                .created(URI.create("/api/tasks/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Status status
    ) {
        return ResponseEntity.ok(taskService.getTasks(priority, status));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDto>> getOverdue() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<TaskDto>> getUnassigned() {
        return ResponseEntity.ok(taskService.getUnassignedTasks());
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskDto> assign(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.assignTaskToUser(taskId, userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDto> changeStatus(@PathVariable Long id, @RequestParam String newStatus) {
        return ResponseEntity.ok(taskService.changeTaskStatus(id, newStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.deleteTaskById(id); // кинет 404, если нет
        return ResponseEntity.noContent().build();
    }
}
