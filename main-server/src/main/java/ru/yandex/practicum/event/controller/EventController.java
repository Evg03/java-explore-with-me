package ru.yandex.practicum.event.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.event.dto.*;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.SortFilter;
import ru.yandex.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final EventService eventService;

    @PostMapping(path = "/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@Valid @RequestBody NewEventDto newEventDto, @PathVariable int userId) {
        return eventService.createEvent(userId, newEventDto);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@Valid @RequestBody UpdateEventUserRequest updateEventUserRequest,
                                @PathVariable int userId,
                                @PathVariable int eventId) {
        return eventService.updateEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto getEventById(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.getEventById(userId, eventId);
    }

    @GetMapping(path = "/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getUserEvents(@PathVariable int userId,
                                             @RequestParam(defaultValue = "0") Integer from,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getUserEvents(userId, from, size);
    }

    @GetMapping(path = "/admin/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAllEvents(@RequestParam(required = false) List<Integer> users,
                                       @RequestParam(required = false) List<String> states,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest,
                                @PathVariable int eventId) {
        return eventService.updateEvent(eventId, updateEventAdminRequest);
    }

    @GetMapping(path = "/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Integer> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                            @RequestParam(required = false) Boolean onlyAvailable,
                                            @RequestParam(required = false) SortFilter sort,
                                            @RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            HttpServletRequest request) {
        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

    @GetMapping(path = "/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto getEventById(@PathVariable int id, HttpServletRequest request) {
        return eventService.getEventById(id, request);
    }


}
