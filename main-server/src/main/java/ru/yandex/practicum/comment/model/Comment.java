package ru.yandex.practicum.comment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    @Column(name = "event_id")
    private Integer event;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private LocalDateTime created = LocalDateTime.now();
}

