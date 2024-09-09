package ru.yandex.practicum.user.service;

import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    List<UserDto> getUsers(List<Integer> ids);

    List<UserDto> getUsers(int from, int size);

    void deleteUser(int userId);
}
