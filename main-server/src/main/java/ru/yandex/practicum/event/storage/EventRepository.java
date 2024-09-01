package ru.yandex.practicum.event.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByInitiatorId(int userId, Pageable pageable);

    List<Event> findByInitiatorIdInAndStateInAndCategoryInAndEventDateAfterAndEventDateBefore(List<Integer> users,
                                                                                              List<String> states,
                                                                                              List<Integer> categories,
                                                                                              LocalDateTime rangeStart,
                                                                                              LocalDateTime rangeEnd,
                                                                                              Pageable pageable);

}
