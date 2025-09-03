package org.tracker.mapper;

import org.tracker.dto.task.TaskCreateDto;
import org.tracker.dto.task.TaskDto;
import org.tracker.model.Task;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;

import java.util.Arrays;

public class TaskMapper {

    // ответ пользователю в виде dto
    public static TaskDto toDto(Task task) {
        if (task == null) return null;
        return new TaskDto(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority() == null ? null : task.getPriority().toString(),
                task.getDueDate(),
                task.getStatus() == null ? null : task.getStatus().toString(),
                task.getAssignee() == null ? null : task.getAssignee().getId());
    }

    // запрос от пользователя на создание таски -> dto в entity
    public static Task toEntity(TaskCreateDto dto) {
        if (dto == null) return null;
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());

        task.setPriority(Arrays.stream(Priority.values())
                .filter(p -> p.name().equalsIgnoreCase(dto.priority()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неверный приоритет")));

        task.setDueDate(dto.dueDate());

        task.setStatus(Arrays.stream(Status.values())
                .filter(p -> p.name().equalsIgnoreCase(dto.status()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неверный статус")));

        return task;
    }
}
