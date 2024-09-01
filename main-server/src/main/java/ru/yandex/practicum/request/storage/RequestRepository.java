package ru.yandex.practicum.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByRequesterOrderById(int userId);

    List<Request> findAllByIdNotAndEventOrderById(int userId, int eventId);

    List<Request> findAllByIdInOrderByCreatedAsc(List<Integer> requestIds);

    long countByEvent(int eventId);
}
