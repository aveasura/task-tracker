package org.tracker.dto.user;

/**
 * Когда клиент делает POST /users и присылает JSON:
 * { "username": "alice", "password": "1234" }
 * → это попадает в UserCreateDto.
 *
 * */

// DTO для входа (создания)
public record UserCreateDto(String name, String email) {}
