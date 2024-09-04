package ru.yandex.practicum.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.event.dto.*;
import ru.yandex.practicum.event.model.*;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.request.model.Status;
import ru.yandex.practicum.request.storage.RequestRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper = new EventMapper();

    @Override
    public EventDto createEvent(int userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InvalidEventDateException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        Integer categoryId = newEventDto.getCategory();
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Категории с id = %s не существует.", categoryId));
        }
        Event event = modelMapper.map(newEventDto, Event.class);
        event.setInitiator(userOptional.get());
        return modelMapper.map(eventRepository.save(event), EventDto.class);
    }

    @Override
    public EventDto updateEvent(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        LocalDateTime eventDate = updateEventUserRequest.getEventDate();
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InvalidEventDateException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        Event event = eventOptional.get();
        if (!event.getInitiator().getId().equals(userId)) {
            throw new InvalidOwnerException(String.format("Пользователь с id = %s " +
                    "не является владельцем события с id = %s.", userId, event.getId()));
        }
        State state = event.getState();
        if (state != State.CANCELED && state != State.PENDING) {
            throw new EventNotEditableException(String.format("Событие с id = %s не может быть отредактировано, " +
                    "так как находится в статусе: %s.", eventId, state.name()));
        }
        Integer categoryId = updateEventUserRequest.getCategory();
        if (categoryId != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isEmpty()) {
                throw new CategoryNotFoundException(String.format("Категории с id = %s не существует.", categoryId));
            }
        }
        if (updateEventUserRequest.getStateAction() == Action.CANCEL_REVIEW) {
            event.setState(State.CANCELED);
        }
        if (updateEventUserRequest.getStateAction() == Action.SEND_TO_REVIEW) {
            event.setState(State.PENDING);
        }
        modelMapper.map(updateEventUserRequest, event);
        EventDto eventDto = modelMapper.map(eventRepository.save(event), EventDto.class);
        long confirmedRequests = requestRepository.countByEventAndStatusLike(eventId, Status.CONFIRMED);
        eventDto.setConfirmedRequests((int) confirmedRequests);
        return eventDto;
    }

    @Override
    public EventDto updateEvent(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        Event event = eventOptional.get();
        State state = event.getState();
        Action action = updateEventAdminRequest.getStateAction();
        if (action != null) {
            if (action == Action.PUBLISH_EVENT) {
                if (state == State.PENDING) {
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                } else {
                    throw new ActionNotAllowedException("Событие можно публиковать " +
                            "только если оно в состоянии ожидания публикации.");
                }
            }
            if (action == Action.REJECT_EVENT) {
                if (state == State.PENDING) {
                    event.setState(State.CANCELED);
                } else {
                    throw new ActionNotAllowedException("Событие можно отклонить, " +
                            "только если оно еще не опубликовано.");
                }
            }
        }
        modelMapper.map(updateEventAdminRequest, event);
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn())) {
            throw new ActionNotAllowedException("Дата начала изменяемого события должна быть " +
                    "не ранее чем за час от даты публикации.");
        }
        EventDto eventDto = modelMapper.map(eventRepository.save(event), EventDto.class);
        long confirmedRequests = requestRepository.countByEventAndStatusLike(eventId, Status.CONFIRMED);
        eventDto.setConfirmedRequests((int) confirmedRequests);
        return eventDto;
    }

    @Override
    public EventDto getEventById(int userId, int eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        Event event = eventOptional.get();
        if (!event.getInitiator().getId().equals(userId)) {
            throw new InvalidOwnerException(String.format("Пользователь с id = %s " +
                    "не является владельцем события с id = %s.", userId, event.getId()));
        }
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        long confirmedRequests = requestRepository.countByEventAndStatusLike(eventId, Status.CONFIRMED);
        eventDto.setConfirmedRequests((int) confirmedRequests);
        return eventDto;
    }

    @Override
    public List<EventShortDto> getUserEvents(int userId, int from, int size) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        return eventRepository.findByInitiatorId(userId,
                        PageRequest.of(from, size, Sort.by("id").ascending()))
                .stream()
                .map(event -> modelMapper.map(event, EventShortDto.class))
                .peek(eventShortDto -> {
                    long confirmedRequests = requestRepository.countByEventAndStatusLike(eventShortDto.getId(), Status.CONFIRMED);
                    eventShortDto.setConfirmedRequests((int) confirmedRequests);
                })
                .toList();
    }

    @Override
    public List<EventDto> getAllEvents(List<Integer> users,
                                       List<String> states,
                                       List<Integer> categories,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       Integer from,
                                       Integer size) {
        List<Event> events = eventRepository.findByInitiatorIdInAndStateInAndCategoryInAndEventDateAfterAndEventDateBefore(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                PageRequest.of(from, size, Sort.by("id").ascending()));
        return events.stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .peek(eventDto -> {
                    long confirmedRequests = requestRepository.countByEventAndStatusLike(eventDto.getId(), Status.CONFIRMED);
                    eventDto.setConfirmedRequests((int) confirmedRequests);
                })
                .toList();
    }

    @Override
    public List<EventShortDto> getAllEvents(String text,
                                            List<Integer> categories,
                                            Boolean paid,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            Boolean onlyAvailable,
                                            SortFilter sortFilter,
                                            Integer from,
                                            Integer size) {
        List<BooleanExpression> predicates = new ArrayList<>();
        if (text != null) {
            predicates.add(QEvent.event.annotation.containsIgnoreCase(text));
            predicates.add(QEvent.event.description.containsIgnoreCase(text));
        }
        if (categories != null) {
            predicates.add(QEvent.event.category.in(categories));
        }
        if (paid != null) {
            predicates.add(QEvent.event.paid.eq(paid));
        }
        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new InvalidDateRangeException("Дата начала диапазона не может быть позже даты конца диапазона.");
            }
            predicates.add(QEvent.event.eventDate.after(rangeStart));
            predicates.add(QEvent.event.eventDate.before(rangeEnd));
        } else {
            predicates.add(QEvent.event.eventDate.after(LocalDateTime.now()));
        }
        predicates.add(QEvent.event.state.eq(State.PUBLISHED));

        BooleanExpression filters = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            BooleanExpression predicate = predicates.get(i);
            filters = filters.and(predicate);
        }
        List<Event> events = eventRepository.findAll(filters, PageRequest.of(from, size)).toList();

        return events.stream()
                .filter(this::isAvailable)
                .map(event -> modelMapper.map(event, EventShortDto.class))
                .peek(eventDto -> {
                    long confirmedRequests = requestRepository.countByEventAndStatusLike(eventDto.getId(), Status.CONFIRMED);
                    eventDto.setConfirmedRequests((int) confirmedRequests);
                })
                .sorted(getSortComparator(sortFilter))
                .toList();
    }

    @Override
    public EventDto getEventById(int id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty() || eventOptional.get().getState() != State.PUBLISHED) {
            throw new EventNotFoundException(String.format("Опубликованного события с id = %s не найдено.", id));
        }
        Event event = eventOptional.get();
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        long confirmedRequests = requestRepository.countByEventAndStatusLike(id, Status.CONFIRMED);
        eventDto.setConfirmedRequests((int) confirmedRequests);
        return eventDto;
    }

    private boolean isAvailable(Event event) {
        return event.getParticipantLimit() > requestRepository.countByEventAndStatusLike(event.getId(),
                Status.CONFIRMED);
    }

    private Comparator<EventShortDto> getSortComparator(SortFilter sortFilter) {
        Comparator<EventShortDto> comparator;
        if (sortFilter != null) {
            if (sortFilter == SortFilter.EVENT_DATE) {
                comparator = (o1, o2) -> o1.getEventDate().compareTo(o2.getEventDate());
            } else {
                comparator = (o1, o2) -> o1.getViews().compareTo(o2.getViews());
            }
        } else {
            comparator = (o1, o2) -> o1.getId().compareTo(o2.getId());
        }
        return comparator;
    }

}
