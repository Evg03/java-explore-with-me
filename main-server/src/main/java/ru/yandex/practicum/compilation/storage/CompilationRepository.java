package ru.yandex.practicum.compilation.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    List<Compilation> findByPinnedIs(boolean pinned, Pageable pageable);
}
