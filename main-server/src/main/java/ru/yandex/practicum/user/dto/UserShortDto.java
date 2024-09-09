package ru.yandex.practicum.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserShortDto {
    private Integer id;
    private String name;
}
