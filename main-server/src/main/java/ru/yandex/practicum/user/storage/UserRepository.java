package ru.yandex.practicum.user.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
