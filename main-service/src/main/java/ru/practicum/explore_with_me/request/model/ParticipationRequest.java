package ru.practicum.explore_with_me.request.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequest {
    //id — уникальный идентификатор заявки
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //created — дата и время создания заявки (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created", nullable = false)
    LocalDateTime created;

    //event — событие
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    //requester — пользователь, отправивший заявку
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    User requester;

    //state — статус заявки
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    ParticipationRequestState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationRequest category = (ParticipationRequest) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}



