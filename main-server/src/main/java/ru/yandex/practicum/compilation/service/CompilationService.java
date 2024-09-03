package ru.yandex.practicum.compilation.service;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;

public interface CompilationService {

    CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto);

    void deleteCompilation(int compId);
}
