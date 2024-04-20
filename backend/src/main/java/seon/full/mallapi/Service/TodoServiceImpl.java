package seon.full.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.domain.Todo;
import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;
import seon.full.mallapi.dto.TodoDTO;
import seon.full.mallapi.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    /**
     * 단건 등록
     * 
     * @param todoDTO
     * @return 신규 등록된 데이터의 인덱스
     */
    @Override
    public Long register(TodoDTO todoDTO) {
        log.info("........");

        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getTno();
    }

    /**
     * 단건 조회
     * @param tno
     * @return 조회된 데이터 반환(단건)
     */
    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> todoOptional = todoRepository.findById(tno);

        Todo todo = todoOptional.orElseThrow();
        return modelMapper.map(todo, TodoDTO.class);
    }

    /**
     * 단건 수정
     * 
     * @param todoDTO
     */
    @Override
    public void modify(TodoDTO todoDTO) {
        Optional<Todo> todoOptional = todoRepository.findById(todoDTO.getTno());
        Todo todo = todoOptional.orElseThrow();

        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeComplete(todoDTO.isComplete());
        todo.changeTitle(todoDTO.getTitle());

        todoRepository.save(todo);
    }

    /**
     * 단건 삭제
     *
     * @param tno : 삭제 대상 index
     * @return none
     */
    @Override
    public void delete(Long tno) {
         todoRepository.deleteById(tno);
    }

    /**
     * 페이징 조회
     * 
     * @param pageRequestDTO
     * @return 페이징에 필요한 정보를 모두 계산하여 리턴 (이전 페이지 / 다음 페이지 현재 페이지/ 페이지 List
     */
    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        //Pageable 선언
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        //조회
        Page<Todo> result = todoRepository.findAll(pageable);

        //todo List 생성
        List<TodoDTO> dtoList = result.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();

        return responseDTO;

    }
}
