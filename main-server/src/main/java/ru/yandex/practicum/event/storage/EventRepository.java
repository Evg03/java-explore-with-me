package ru.yandex.practicum.event.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
