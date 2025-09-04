package org.tracker.dto.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

// запрос от пользователя на добавление таски -> преобразовать в entity
public record TaskCreateDto(
        @NotBlank(message = "Название не может быть пустым")
        String title,

        String description,

        @NotBlank(message = "Приоритет обязателен")
        String priority,

        @FutureOrPresent(message = "Дата дедлайна не может быть в прошлом")
        LocalDateTime dueDate,

        @NotBlank(message = "Статус обязателен")
        String status
) {}
