package seon.full.mallapi.service;

import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;
import seon.full.mallapi.dto.ProductDTO;

@Transactional
public interface ProductService {
    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO get(Long pno);

    void modify(ProductDTO productDTO);

    void remove(Long pno);
}
