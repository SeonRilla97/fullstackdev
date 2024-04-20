package seon.full.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDTO pageRequestDTO;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount){
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount;
        //todo 예시 : size:10 page 12

        // 20 page
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        // 11 page
        int start = end-9;

        // 목록의 끝이 20 page가 아니면 (ex 끝이 17페이지)
        int last = (int) (Math.ceil((totalCount / (double)pageRequestDTO.getSize())));
        end = end > last ? last : end;

        // 1 ~ 10 페이지로 이동 가능 여부
        this.prev = start > 1;

        // 21~30 페이지로 이동 가능 여부
        this.next = totalCount > end * pageRequestDTO.getSize();

        //11 ~20 Page 목록 나열
        this.pageNumList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        //  1 ~ 10 페이지로 이동
        if(prev) {
            this.prevPage = start -1;
        }
        //  21 ~ 30 페이지로 이동
        if(next) {
            this.nextPage = end + 1;
        }

        // 현재 페이지가 속한 페이지 리스트 크기
        this.totalPage = this.pageNumList.size();
        // 현재 페이지
        this.current = pageRequestDTO.getPage();
    }
}
