package ru.yandex.practicum.compilation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.event.model.Event;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToMany
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;
    private Boolean pinned;
}
