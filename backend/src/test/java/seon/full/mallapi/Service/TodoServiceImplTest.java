package seon.full.mallapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
class TodoServiceImplTest {

    @Autowired
    private TodoService todoService;

    @Test
    public void testRegister() {

        TodoDTO todo = TodoDTO.builder()
                .title("서비스 테스트")
                .writer("tester")
                .dueDate(LocalDate.of(24, 3, 27))
                .build();

        Long tno = todoService.register(todo);

        log.info("tno: " + tno);
    }

    @Test
    public void selectTest() {

        TodoDTO todoDTO = todoService.get(101L);
        log.info("todoDTO : " + todoDTO);

    }

    @Test
    public void modifyTest() {
        TodoDTO todo = TodoDTO.builder()
                .tno(101L)
                .title("Modify서비스 테스트")
                .writer("modiTester")
                .dueDate(LocalDate.of(24, 3, 27))
                .build();

        todoService.modify(todo);

        TodoDTO todoDTO = todoService.get(todo.getTno());

        log.info("todo modify " + todoDTO);
    }

    @Test
    public void deleteTest() {
        Long tno = 101L;

        todoService.delete(tno);


        TodoDTO todoDTO = todoService.get(tno);
        log.info("todo modify " + todoDTO);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(12)
                .size(10)
                .build();
        PageResponseDTO<TodoDTO> response = todoService.list(pageRequestDTO);

        log.info("This is " + response);
    }

}