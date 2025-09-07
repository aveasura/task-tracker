package org.tracker.dto.status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(
        @NotNull(message = "Нужно указать статус заявки: new/in_progress/done")
        @NotBlank(message = "Статус не может быть пустым")
        String status
) {
}
