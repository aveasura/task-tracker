package org.tracker.mapper;

import org.springframework.stereotype.Component;
import org.tracker.dto.user.UserCreateDto;
import org.tracker.dto.user.UserDto;
import org.tracker.model.User;

@Component
public class UserMapper {

    // из Entity в DTO (чтобы отдать наружу)
    public UserDto toDto(User user) {
        if (user == null) return null;
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    // из CreateDto в Entity (чтобы сохранить в бд данные которые послал пользователь)
    public User toEntity(UserCreateDto dto) {
        if (dto == null) return null;
        return new User(dto.name(), dto.email());
    }
}
