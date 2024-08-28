package ru.yandex.practicum.user.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    List<UserDto> getUsers(List<Integer> ids);

    List<UserDto> getUsers(int from, int size);

    void deleteUser(int userId);
}
