package ru.yandex.practicum.request.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "event_id")
    private Integer event;
    private LocalDateTime created;
    @Column(name = "user_id")
    private Integer requester;
    @Enumerated(EnumType.STRING)
    private Status status;
}
