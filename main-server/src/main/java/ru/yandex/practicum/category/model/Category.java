package ru.yandex.practicum.category.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.user.dto.UserDto;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "categories")
@SqlResultSetMapping(
        name = "category_query_dto",
        classes = @ConstructorResult(
                targetClass = CategoryDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "category_native_query_dto",
        query = "SELECT * " +
                "FROM categories AS cat " +
                "ORDER BY cat.id " +
                "OFFSET :offset " +
                "LIMIT :size",
        resultSetMapping = "category_query_dto"
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
