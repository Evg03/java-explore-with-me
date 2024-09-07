package ru.yandex.practicum.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.dto.UpdateStatusRequestDto;
import ru.yandex.practicum.request.dto.UpdateStatusResultDto;
import ru.yandex.practicum.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping(path = "/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable int userId, @RequestParam int eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequest(@PathVariable int userId, @PathVariable int requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping(path = "/users/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getUserRequests(@PathVariable int userId) {
        return requestService.getUserRequests(userId);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getUserEventRequests(@PathVariable int userId, @PathVariable int eventId) {
        return requestService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public UpdateStatusResultDto updateRequestsStatus(@PathVariable int userId, @PathVariable int eventId,
                                                      @Valid @RequestBody UpdateStatusRequestDto updateStatusRequestDto) {
        return requestService.updateRequestsStatus(userId, eventId, updateStatusRequestDto);
    }
}
