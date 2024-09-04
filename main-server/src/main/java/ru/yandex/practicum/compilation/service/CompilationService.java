package ru.yandex.practicum.compilation.service;

import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;
import java.util.Optional;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(int compId);

    CompilationDto updateCompilation(UpdateCompilationRequest updateCompilationRequest, int compId);

    List<CompilationDto> getCompilations(Optional<Boolean> pinned, int from, int size);

    CompilationDto getCompilationById(int compId);
}
