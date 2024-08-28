package ru.yandex.practicum.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.yandex.practicum.event.model.Location;

import java.time.LocalDateTime;

@Data
@Jacksonized
@Builder
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private final String annotation;
    @NotNull
    private final Integer category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private final String description;
    @NotNull
    private final LocalDateTime eventDate;
    @NotNull
    private final Location location;
    @Builder.Default
    private final Boolean paid = false;
    @Builder.Default
    private final Integer participantLimit = 0;
    @Builder.Default
    private final Boolean requestModeration = false;
    @NotBlank
    @Size(min = 3, max = 120)
    private final String title;
}
