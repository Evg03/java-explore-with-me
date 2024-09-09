package ru.yandex.practicum.request.service;

import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.dto.UpdateStatusRequestDto;
import ru.yandex.practicum.request.dto.UpdateStatusResultDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto createRequest(int userId, int eventId);

    List<ParticipationRequestDto> getUserRequests(int userId);

    ParticipationRequestDto cancelRequest(int userId, int requestId);

    List<ParticipationRequestDto> getUserEventRequests(int userId, int eventId);

    UpdateStatusResultDto updateRequestsStatus(int userId, int eventId, UpdateStatusRequestDto updateStatusRequestDto);
}
