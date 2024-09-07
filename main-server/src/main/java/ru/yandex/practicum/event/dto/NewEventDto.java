package ru.yandex.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private final LocalDateTime eventDate;
    @NotNull
    private final Location location;
    @Builder.Default
    private final Boolean paid = false;
    @Builder.Default
    @PositiveOrZero
    private final Integer participantLimit = 0;
    @Builder.Default
    private final Boolean requestModeration = true;
    @NotBlank
    @Size(min = 3, max = 120)
    private final String title;
}
