package ru.yandex.practicum.user.storage;

import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    /*@Query("SELECT new ru.yandex.practicum.user.dto.UserDto(u.id, u.name, u.email) " +
            "FROM User AS u " +
            "ORDER BY u.name " +
            "OFFSET :offset " +
            "LIMIT :size")
    List<UserDto> findWithOffsetAndLimit(int offset, int size);*/
    /*@Query(value = "SELECT * " +
            "FROM users AS u " +
            "ORDER BY u.name " +
            "OFFSET :offset " +
            "LIMIT :size", nativeQuery = true)
    List<UserDto> findWithOffsetAndLimit(int offset, int size);*/

    @Query(name = "user_native_query_dto", nativeQuery = true)
    List<UserDto> findWithOffsetAndLimit(int offset, int size);
}
