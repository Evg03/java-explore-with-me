package ru.yandex.practicum.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private final Integer id;
    private final String name;
    private final String email;
}
