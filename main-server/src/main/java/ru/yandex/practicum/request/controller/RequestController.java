package ru.yandex.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.request.service.RequestService;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
}
