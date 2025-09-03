package org.tracker.dto.user;

/**
 * Когда нужно вернуть наружу созданного пользователя
 * берем User и маппим его в UserDto, чтобы не светить например пароль(если такое поле есть)
 * */

// DTO для ответа (ответ клиенту)
public record UserDto(Long id, String name, String email) {}