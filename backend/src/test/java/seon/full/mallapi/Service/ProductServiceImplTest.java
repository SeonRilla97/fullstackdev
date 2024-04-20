package seon.full.mallapi.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;
import seon.full.mallapi.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Test
    public void testList() {

        //1 page , 10 size
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ProductDTO> result = productService.getList(pageRequestDTO);

        log.info("test Start!!!!");
        result.getDtoList().forEach(dto->log.info(dto));
    }

    @Test
    public void testRegister() {
        ProductDTO productDTO = ProductDTO.builder()
                .pname("새로운 상품")
                .pdesc("신규 추가 상품입니다.")
                .price(1000)
                .build();

        productDTO.setUploadFileNames(
                List.of(
                        UUID.randomUUID()+"_"+"Test1.jpg",
                        UUID.randomUUID()+"_"+"Test2.jpg"
                )
        );

        productService.register(productDTO);
    }

    @Test
    public void testRead() {
        Long pno=12L;

        ProductDTO productDTO = productService.get(pno);

        log.info(productDTO.toString());
        log.info(productDTO.getUploadFileNames());
    }
}