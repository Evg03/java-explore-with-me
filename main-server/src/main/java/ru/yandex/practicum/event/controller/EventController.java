package ru.yandex.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.event.service.EventService;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final ModelMapper modelMapper = new ModelMapper();

}
