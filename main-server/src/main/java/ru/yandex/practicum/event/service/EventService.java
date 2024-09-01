package ru.yandex.practicum.event.service;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.event.dto.*;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.SortFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto createEvent(int userId, NewEventDto newEventDto);

    EventDto updateEvent(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    EventDto updateEvent(int eventId, UpdateEventAdminRequest updateEventAdminRequest);

    EventDto getEventById(int userId, int eventId);

    List<EventShortDto> getUserEvents(int userId, int from, int size);

    List<EventDto> getAllEvents(List<Integer> users,
                                List<String> states,
                                List<Integer> categories,
                                LocalDateTime rangeStart,
                                LocalDateTime rangeEnd,
                                Integer from,
                                Integer size);

    EventShortDto getAllEvents(String text,
                               List<Integer> categories,
                               Boolean paid,
                               LocalDateTime rangeStart,
                               LocalDateTime rangeEnd,
                               Boolean onlyAvailable,
                               SortFilter sort,
                               Integer from,
                               Integer size);

    EventDto getEventById(int id);
}
