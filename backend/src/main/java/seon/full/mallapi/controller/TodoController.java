package seon.full.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import seon.full.mallapi.service.TodoService;
import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;
import seon.full.mallapi.dto.TodoDTO;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/todo")
public class TodoController {
    private final TodoService service;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable(name = "tno") Long tno){
        return service.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO){
        log.info("todo List Select :: " + pageRequestDTO);
        return service.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO){
        log.info("TodoDTO: " + todoDTO);

        Long tno = service.register(todoDTO);

        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(
            @PathVariable(name = "tno") Long tno,
            @RequestBody TodoDTO todoDTO
    ) {
        todoDTO.setTno(tno);

        log.info("Modify: " + todoDTO);

        service.modify(todoDTO);


        return Map.of("RESULT", "SUCCESS");
    }


    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable(name = "tno") Long tno){
        log.info("Remove: " + tno);

        service.delete(tno);

        return Map.of("RESULT", "SUCCESS");
    }
}
