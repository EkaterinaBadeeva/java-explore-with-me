package ru.practicum.explore_with_me.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.ViewStatsDtoProjection;
import ru.practicum.explore_with_me.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query("SELECT h.app AS app, h.uri AS uri, COUNT(h.id) AS hits " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.id) DESC")
    List<ViewStatsDtoProjection> findAllHitsAndTimestampBetweenStartEnd(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT h.app AS app, h.uri AS uri, COUNT(distinct h.ip) AS hits " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(distinct h.ip) DESC")
    List<ViewStatsDtoProjection> findAllHitsAndTimestampBetweenStartEndAndUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT h.app AS app, h.uri AS uri, COUNT(distinct h.ip) AS hits " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end and h.uri in :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(distinct h.ip) DESC")
    List<ViewStatsDtoProjection> findAllHitsAndTimestampBetweenStartEndAndUrisInAndUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);

    @Query("SELECT h.app AS app, h.uri AS uri, COUNT(h.id) AS hits " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end and h.uri in :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.id) DESC")
    List<ViewStatsDtoProjection> findAllHitsAndTimestampBetweenStartEndAndUrisIn(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);

}
