package ru.practicum.explore_with_me.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.compilation.dao.CompilationRepository;
import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.dto.UpdateCompilationRequest;
import ru.practicum.explore_with_me.compilation.mapper.CompilationMapper;
import ru.practicum.explore_with_me.compilation.model.Compilation;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.service.EventService;
import ru.practicum.explore_with_me.exceptions.CommonException;
import ru.practicum.explore_with_me.exceptions.NotFoundException;
import ru.practicum.explore_with_me.exceptions.ValidationException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventService eventService;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto compilationDto) {
        log.info("Добавление новой подборки (подборка может не содержать событий).");
        Compilation compilation = CompilationMapper.mapToCompilation(compilationDto);
        checkConditions(compilation);
        checkTitle(compilation);

        List<Long> eventIds = compilationDto.getEvents();
        List<Event> events = List.of();

        if (eventIds == null || eventIds.isEmpty()) {
            compilation.setEvents(events);
        } else {
            events = eventService.findEventsByIds(eventIds);
        }

        compilation.setEvents(events);
        compilation.setEvents(events);
        compilation = compilationRepository.save(compilation);

        return CompilationMapper.mapToCompilationDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(UpdateCompilationRequest compilationDto, Long compId) {
        log.info("Обновить информацию о подборке.");

        checkId(compId);

        Compilation newCompilation = CompilationMapper.mapToCompilation(compilationDto);
        Compilation oldCompilation = findCompilationById(compId);
        String newTitle = newCompilation.getTitle();

        if (newTitle != null && !newTitle.equals(oldCompilation.getTitle())) {
            checkTitle(newCompilation);
            oldCompilation.setTitle(newTitle);
        }

        Boolean newPinned = newCompilation.getPinned();
        if (newPinned != null) {
            oldCompilation.setPinned(newPinned);
        }

        List<Long> eventIds = compilationDto.getEvents();
        List<Event> newEvents;

        if (eventIds != null) {
            newEvents = eventService.findEventsByIds(eventIds);
            oldCompilation.setEvents(newEvents);
        }

        return CompilationMapper.mapToCompilationDto(oldCompilation);
    }

    @Override
    @Transactional
    public void deleteCompilationById(Long compId) {
        log.info("Удаление подборки событий.");

        checkId(compId);
        findCompilationById(compId);

        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        log.info("Получение информации о категориях.");

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        Page<Compilation> compilationPage;

        if (pinned != null) {
            compilationPage = compilationRepository.findByPinned(pinned, pageable);
        } else {
            compilationPage = compilationRepository.findAll(pageable);
        }

        return compilationPage.getContent()
                .stream()
                .map(CompilationMapper::mapToCompilationDto)
                .toList();
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        log.info("Получение подборки событий по его id.");
        checkId(compId);
        Compilation compilation = findCompilationById(compId);
        return CompilationMapper.mapToCompilationDto(compilation);
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkTitle(Compilation compilation) {
        for (Compilation comp : compilationRepository.findAll()) {
            if (comp.getTitle().equals(compilation.getTitle())) {
                log.warn("Заголовок уже существует.");
                throw new CommonException("Заголовок с title = " + compilation.getTitle() + " уже существует");
            }
        }
    }

    private void checkConditions(Compilation compilation) {
        if (compilation.getTitle().isEmpty()) {
            log.warn("Задан пустой заголовок подборки.");
            throw new ValidationException("Задан пустой заголовок подборки.");
        }
    }

    private Compilation findCompilationById(Long comId) {
        return compilationRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Подборка событий не найдена"));
    }
}
