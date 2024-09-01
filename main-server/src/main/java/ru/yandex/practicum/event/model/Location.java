package ru.yandex.practicum.event.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
//@Entity
//@Table(name = "locations")
public class Location {
    private final float lat;
    private final float lon;
}
