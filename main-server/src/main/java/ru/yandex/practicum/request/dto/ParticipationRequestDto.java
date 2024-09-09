package ru.yandex.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.request.model.Status;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ParticipationRequestDto {
    private Integer id;
    private Integer event;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Integer requester;
    @Enumerated(EnumType.STRING)
    private Status status;

}
