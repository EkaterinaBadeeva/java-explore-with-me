package ru.practicum.explore_with_me.compilation.service;


import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto compilationDto);

    CompilationDto updateCompilation(NewCompilationDto compilationDto, Long compId);

    void deleteCompilationById(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}
