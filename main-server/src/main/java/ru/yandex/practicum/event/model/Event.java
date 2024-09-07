package ru.yandex.practicum.event.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String annotation;
    @Column(name = "category_id")
    private Integer category;
    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;
    private Float latitude;
    private Float longitude;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;
    private String title;
}
