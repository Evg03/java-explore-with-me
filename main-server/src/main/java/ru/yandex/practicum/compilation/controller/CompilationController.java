package ru.yandex.practicum.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.service.CompilationService;
import ru.yandex.practicum.event.dto.EventDto;
import ru.yandex.practicum.event.dto.NewEventDto;

@RestController
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;

    @PostMapping(path = "/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping(path = "/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable int compId) {
        compilationService.deleteCompilation(compId);
    }
}
