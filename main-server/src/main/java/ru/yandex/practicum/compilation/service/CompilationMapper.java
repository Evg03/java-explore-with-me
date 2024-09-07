package ru.yandex.practicum.compilation.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.compilation.model.Compilation;

public class CompilationMapper extends ModelMapper {
    public CompilationMapper() {
        TypeMap<UpdateCompilationRequest, Compilation> updateCompilationMap = this.createTypeMap(UpdateCompilationRequest.class,
                Compilation.class);
        TypeMap<NewCompilationDto, Compilation> newCompilationTypeMap = this.createTypeMap(NewCompilationDto.class,
                Compilation.class);
        newCompilationTypeMap.addMappings(mapper -> mapper.skip(Compilation::setId));
        updateCompilationMap.addMappings(mapper -> mapper.skip(Compilation::setId));
        this.getConfiguration().setSkipNullEnabled(true);
    }
}
