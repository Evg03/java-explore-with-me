package ru.yandex.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.State;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.dto.UpdateStatusRequestDto;
import ru.yandex.practicum.request.dto.UpdateStatusResultDto;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.request.model.Status;
import ru.yandex.practicum.request.storage.RequestRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ParticipationRequestDto createRequest(int userId, int eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        Event event = eventOptional.get();
        if (event.getInitiator().getId().equals(userId)) {
            throw new ActionNotAllowedException("Инициатор события не может добавить " +
                    "запрос на участие в своём событии");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ActionNotAllowedException("Нельзя участвовать в неопубликованном событии.");
        }
        long requestsCount = requestRepository.countByEventAndStatusLike(eventId, Status.CONFIRMED);
        Integer limit = event.getParticipantLimit();
        if (limit != 0 && limit == requestsCount) {
            throw new ActionNotAllowedException("Достигнут лимит запросов");
        }
        Status status = limit == 0 ? Status.CONFIRMED : event.getRequestModeration() ? Status.PENDING : Status.CONFIRMED;
        Request request = new Request(null, eventId, LocalDateTime.now(), userId, status);
        Request savedRequest = requestRepository.save(request);
        return modelMapper.map(savedRequest, ParticipationRequestDto.class);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        return requestRepository.findAllByRequesterOrderById(userId).stream()
                .map(request -> modelMapper.map(request, ParticipationRequestDto.class))
                .toList();
    }

    @Override
    public ParticipationRequestDto cancelRequest(int userId, int requestId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        User user = userOptional.get();
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty()) {
            throw new RequestNotFoundException(String.format("Заявки с id = %s не существует.", requestId));
        }
        Request request = requestOptional.get();
        if (!user.getId().equals(request.getRequester())) {
            throw new InvalidOwnerException(String.format("Пользователь с id = %s " +
                    "не является владельцем заявки с id = %s.", userId, requestId));
        }
        request.setStatus(Status.CANCELED);
        Request savedRequest = requestRepository.save(request);
        return modelMapper.map(savedRequest, ParticipationRequestDto.class);
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(int userId, int eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        return requestRepository.findAllByIdNotAndEventOrderById(userId, eventId).stream()
                .map(request -> modelMapper.map(request, ParticipationRequestDto.class))
                .toList();
    }

    @Override
    public UpdateStatusResultDto updateRequestsStatus(int userId, int eventId, UpdateStatusRequestDto updateStatusRequestDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        Event event = eventOptional.get();
        Integer limit = event.getParticipantLimit();
        Status updateStatus = updateStatusRequestDto.getStatus();

        if (limit != 0 && updateStatus != Status.REJECTED) {
            long requestsCount = requestRepository.countByEventAndStatusLike(eventId, Status.CONFIRMED);
            if (limit <= requestsCount) {
                throw new ActionNotAllowedException("Достигнут лимит запросов!");
            }
        }
        List<Request> requests = requestRepository.findAllByIdInOrderByCreatedAsc(updateStatusRequestDto.getRequestIds());
        if (event.getRequestModeration() && updateStatus == Status.CONFIRMED) {
            confirmRequests(requests, limit);
        }
        if (updateStatus == Status.REJECTED) {
            rejectRequests(requests);
        }
        List<Request> savedRequests = requestRepository.saveAll(requests);
        List<ParticipationRequestDto> confirmedRequests = savedRequests.stream()
                .filter(request -> request.getStatus() == Status.CONFIRMED)
                .map(request -> modelMapper.map(request, ParticipationRequestDto.class))
                .toList();
        List<ParticipationRequestDto> rejectedRequests = savedRequests.stream()
                .filter(request -> request.getStatus() == Status.REJECTED)
                .map(request -> modelMapper.map(request, ParticipationRequestDto.class))
                .toList();
        return new UpdateStatusResultDto(confirmedRequests, rejectedRequests);
    }

    private void validateStatus(Request request) {
        if (request.getStatus() != Status.PENDING) {
            throw new ActionNotAllowedException("Cтатус можно изменить только у заявок, " +
                    "находящихся в состоянии ожидания.");
        }
    }

    private void confirmRequests(List<Request> requests, int limit) {
        if (limit == 0) {
            for (Request request : requests) {
                validateStatus(request);
                request.setStatus(Status.CONFIRMED);
            }
        } else {
            for (Request request : requests) {
                validateStatus(request);
                if (limit > 0) {
                    request.setStatus(Status.CONFIRMED);
                    limit--;
                } else {
                    request.setStatus(Status.REJECTED);
                }
            }
        }
    }

    private void rejectRequests(List<Request> requests) {
        for (Request request : requests) {
            validateStatus(request);
            request.setStatus(Status.REJECTED);
        }
    }
}
