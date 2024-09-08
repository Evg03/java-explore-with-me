package ru.yandex.practicum.compilation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
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
    private final ModelMapper modelMapper = new CompilationMapper();

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = modelMapper.map(newCompilationDto, Compilation.class);
        List<Integer> eventsIds = newCompilationDto.getEvents();
        List<Event> events = eventRepository.findAllById(eventsIds);
        compilation.setEvents(events);
        Compilation savedCompilation = compilationRepository.save(compilation);
        return modelMapper.map(savedCompilation, CompilationDto.class);
    }

    @Override
    @Transactional
    public void deleteCompilation(int compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(UpdateCompilationRequest updateCompilationRequest, int compId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compId);
        if (compilationOptional.isEmpty()) {
            throw new CompilationNotFoundException(String.format("Подборки с id = %s не существует.", compId));
        }
        Compilation compilation = compilationOptional.get();
        modelMapper.map(updateCompilationRequest, compilation);
        List<Event> events = eventRepository.findAllById(updateCompilationRequest.getEvents());
        compilation.setEvents(events);
        Compilation savedCompilation = compilationRepository.save(compilation);
        return modelMapper.map(savedCompilation, CompilationDto.class);
    }

    @Override
    public List<CompilationDto> getCompilations(Optional<Boolean> pinned, int from, int size) {
        List<Compilation> compilations;
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by("id").ascending());
        if (pinned.isPresent()) {
            compilations = compilationRepository.findByPinnedIs(pinned.get(), pageRequest);
        } else {
            compilations = compilationRepository.findAll(pageRequest).toList();
        }
        return compilations.stream().map(compilation -> modelMapper.map(compilation, CompilationDto.class)).toList();
    }

    @Override
    public CompilationDto getCompilationById(int compId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compId);
        if (compilationOptional.isEmpty()) {
            throw new CompilationNotFoundException(String.format("Подборки с id = %s не существует.", compId));
        }
        return modelMapper.map(compilationOptional.get(), CompilationDto.class);
    }
}
