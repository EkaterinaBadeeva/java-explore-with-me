# java-explore-with-me
Template repository for ExploreWithMe project.
---

Ссылка на PR


# Дополнительная функциональность "Рейтинги"

Возможность ставить лайк/дизлайк событию.
Формирование рейтинга мероприятий и рейтинга их авторов.
Возможность сортировки событий в зависимости от рейтингов.
---

### Поставить оценку событию

`POST /users/{userId}/ratings?eventId={eventId}&state={state}`

- оценку можно поставить только если пользователь участвовал в событии
- нельзя поставить больше одной оценки одному событию

### Изменить оценку событию
`PATCH /users/{userId}/ratings?eventId={eventId}&state={state}`

### Удалить оценку событию
`DELETE /users/{userId}/ratings?eventId={eventId}`

### Получить информацию о всех оценках события текущего пользователя
`GET /users/{userId}/ratings?eventId={eventId}`


**Возможность сортировки событий в зависимости от рейтингов добавлена:**

`GET /events`

**Pейтинг событий и рейтинг их авторов, можно увидеть:**

- `GET /events`
- `GET /events/{id}`
- `GET /users/{user_id}/events/{event_id}`