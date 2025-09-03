package org.tracker.dto.task;

import java.time.LocalDateTime;

// запрос от пользователя на добавление таски -> преобразовать в entity
public record TaskCreateDto(String title, String description, String priority,
                            LocalDateTime dueDate, String status) {}
