package seon.full.mallapi.service;

import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;
import seon.full.mallapi.dto.TodoDTO;

public interface TodoService {
    Long register(TodoDTO todoDTO);

    TodoDTO get(Long tno);

    void modify(TodoDTO todoDTO);
    void delete(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
