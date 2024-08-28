package ru.yandex.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.compilation.service.CompilationService;

@RestController
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;


}
