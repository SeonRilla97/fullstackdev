package seon.full.mallapi.repository;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test() {
        log.info("------------------");
        log.info(todoRepository);

        for (int i = 0; i < 100; i++) {

            Todo todo = Todo.builder()
                    .title("title..." + i+100)
                    .dueDate(LocalDate.of(2023, 12, 31))
                    .writer("user" + i+100)
                    .build();
            todoRepository.save(todo);
        }
    }

    @Test
    public void testRead() {
        Long tno = 33L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info(todo);
    }

    @Test
    public void testModify() {
        Long tno = 33L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();
        log.info(todo);
        todo.changeTitle("Modified 33s...");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2023,10,10));

        todoRepository.save(todo);
        log.info(todo);
    }

    @Test
    public void testDelete() {
        Long tno = 1L;

        todoRepository.deleteById(1L);
    }

    @Test
    public void testPage() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());

        result.getContent().stream().forEach(todo-> log.info(todo));

    }

}