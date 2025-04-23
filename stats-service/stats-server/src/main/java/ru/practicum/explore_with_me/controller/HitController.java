package ru.practicum.explore_with_me.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.practicum.explore_with_me.HitDto;
import ru.practicum.explore_with_me.ViewStatsDto;
import ru.practicum.explore_with_me.service.HitService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    //POST /hit
    //Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto saveHit(@Valid @RequestBody HitDto hitDto) {
        return hitService.saveHit(hitDto);
    }

    //GET /stats?start={start}&end={end}&uris={uris}&unique={unique}
    //Получение статистики по посещениям
    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ViewStatsDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                             @RequestParam(required = false) List<String> uris,
                                             @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return hitService.getStats(start, end, uris, unique);
    }
}