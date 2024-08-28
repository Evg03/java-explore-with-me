package ru.yandex.practicum.user.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT new ru.yandex.practicum.user.dto.UserDto(u.id, u.name, u.email) " +
            "FROM User AS u " +
            "ORDER BY u.name " +
            "OFFSET :offset " +
            "LIMIT :size")
    List<UserDto> findWithOffsetAndLimit(int from, int size);
}
