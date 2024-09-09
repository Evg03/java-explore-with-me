package ru.yandex.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.yandex.practicum.event.dto.*;

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

    List<EventShortDto> getAllEvents(String text,
                                     List<Integer> categories,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     Boolean onlyAvailable,
                                     SortFilter sortFilter,
                                     Integer from,
                                     Integer size,
                                     HttpServletRequest request);

    EventDto getEventById(int id, HttpServletRequest request);
}
