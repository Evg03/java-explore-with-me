package ru.yandex.practicum.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.user.dto.UserDto;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users")
@SqlResultSetMapping(
        name = "user_query_dto",
        classes = @ConstructorResult(
                targetClass = UserDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "email", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "user_native_query_dto",
        query = "SELECT * " +
                "FROM users AS u " +
                "ORDER BY u.id " +
                "OFFSET :offset " +
                "LIMIT :size",
        resultSetMapping = "user_query_dto"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
}

