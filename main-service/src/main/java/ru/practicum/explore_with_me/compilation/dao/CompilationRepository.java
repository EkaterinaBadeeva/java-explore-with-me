package ru.practicum.explore_with_me.compilation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

}
