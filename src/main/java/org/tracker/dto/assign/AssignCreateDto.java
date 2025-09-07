package org.tracker.dto.assign;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssignCreateDto(
        @NotNull(message = "Нужно указать userId")
        @Positive(message = "userId должен быть > 0")
        Long userId
) {

}
