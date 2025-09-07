package org.tracker.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.tracker.dto.assign.AssignCreateDto;
import org.tracker.dto.status.StatusUpdateRequest;
import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@Valid @RequestBody TaskCreateDto dto) {
        TaskDto created = taskService.createTask(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // todo засунуть /overdue и /unassigned в getTasks как query параметр.(юзать Specifications findAll(spec, pageable))
    //  и прикрутить пагинацию
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

    @PutMapping("/{id}/assignee")
    public ResponseEntity<TaskDto> assign(@PathVariable Long id,
                                          @Valid @RequestBody AssignCreateDto body) {
        return ResponseEntity.ok(taskService.assignTaskToUser(id, body.userId()));
    }

    @DeleteMapping("/{id}/assignee")
    public ResponseEntity<Void> unassign(@PathVariable Long id) {
        taskService.unassignTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDto> changeStatus(@PathVariable Long id,
                                                @Valid @RequestBody StatusUpdateRequest dto) {
        return ResponseEntity.ok(taskService.changeTaskStatus(id, dto.status()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.deleteTaskById(id); // кинет 404, если нет
        return ResponseEntity.noContent().build();
    }
}
