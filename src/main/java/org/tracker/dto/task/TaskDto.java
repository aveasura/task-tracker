package org.tracker.dto.task;

import java.time.LocalDateTime;

// ответ пользователю
public record TaskDto(Long id, String title, String description,
                      String priority, LocalDateTime dueDate,
                      String status, Long assigneeId) {}