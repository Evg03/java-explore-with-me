package ru.yandex.practicum.request.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    private final Integer id;
    private final Integer event;
    private final LocalDateTime created;
    private final Integer requester;
    private final String status;
}
