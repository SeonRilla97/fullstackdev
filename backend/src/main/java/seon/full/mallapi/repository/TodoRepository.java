package seon.full.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seon.full.mallapi.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
