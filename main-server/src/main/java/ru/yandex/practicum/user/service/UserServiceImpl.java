package ru.yandex.practicum.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = modelMapper.map(newUserRequest, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids) {
        return userRepository.findAllById(ids).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public List<UserDto> getUsers(int from, int size) {
        return userRepository.findAll(PageRequest.of(from, size, Sort.by("id").ascending()))
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

}
