package ru.yandex.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.compilation.storage.CompilationRepository;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.exception.CompilationNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = modelMapper.map(newCompilationDto, Compilation.class);
        List<Integer> eventsIds = newCompilationDto.getEvents();
        List<Event> events = eventRepository.findAllById(eventsIds);
        compilation.setEvents(events);
        Compilation savedCompilation = compilationRepository.save(compilation);
        return modelMapper.map(savedCompilation, CompilationDto.class);
    }

    @Override
    public void deleteCompilation(int compId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compId);
        if (compilationOptional.isEmpty()) {
            throw new CompilationNotFoundException(String.format("Подборки с id = %s не существует.", compId));
        }
        compilationRepository.deleteById(compId);
    }
}
