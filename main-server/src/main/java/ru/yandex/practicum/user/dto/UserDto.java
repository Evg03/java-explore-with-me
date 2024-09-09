package ru.yandex.practicum.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private Integer id;
    private  String name;
    private  String email;
}
