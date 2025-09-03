package org.tracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Когда клиент делает POST /users и присылает JSON:
 * { "username": "alice", "password": "1234" }
 * → это попадает в UserCreateDto.
 *
 * */

// DTO для входа (создания)
public record UserCreateDto(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 2, max = 30, message = "Длинна имени может быть от 2 до 30 символов")
        String name,

        @NotBlank(message = "Email обязателен")
        @Email(message = "Некорректный email")
        String email
) {}
