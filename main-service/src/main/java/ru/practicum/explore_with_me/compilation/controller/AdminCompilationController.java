package ru.practicum.explore_with_me.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.service.CompilationService;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    //POST /admin/compilations
    // добавить новую подборку (подборка может не содержать событий)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }

    //PATCH /admin/compilations/{compId}
    // обновить информацию о подборке
    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@Valid @RequestBody NewCompilationDto compilationDto,
                                      @PathVariable Long compId) {
        return compilationService.updateCompilation(compilationDto, compId);
    }

    //DELETE /admin/compilations/{compId}
    // удалить подборку
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilationById(compId);
    }
}
