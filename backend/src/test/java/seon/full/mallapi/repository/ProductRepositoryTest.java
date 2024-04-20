package seon.full.mallapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.domain.Product;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testInsert () {
        for (int i = 0 ; i < 10; i ++) {
            Product product = Product.builder()
                    .pname("상품" + i)
                    .price(100 * i)
                    .pdesc("상품설명 " + i)
                    .build();
            product.addImageString(UUID.randomUUID().toString()+"_"+"IMAGE1.jpg");
            product.addImageString(UUID.randomUUID().toString()+"_"+"IMAGE2.jpg");

            productRepository.save(product);
        }
    }


    /**
     * Fetch Join이 적용되지 않아 N+1 문제 발생
     */
    @Transactional //2개의 Entity(Table) 에 접근하기 때문
    @Test
    public void testRead() {
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product.toString());
        log.info(product.getImageList().toString());

    }

    /**
     * Fetch Join (GraphEntity 선언)을 통해 N+1 문제 해결
     */
    @Test
    public void testRead2() {
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        product.getImageList();

        log.info(product.toString());
        log.info(product.getImageList().toString());
    }

    /**
     * 상품 삭제처리
     */
    @Commit
    @Transactional
    @Test
    public  void  testDelete() {
        Long pno = 2L;
        productRepository.updateToDelete(pno, true);
    }


    /**
     * 두 객체의 영속성을 위해 Left Join으로 불렀지만, 각자 조회 쿼리가 따로 나갔음 Embbedable 과 ElementalCollection을 했지만 이러면 의미가 있는지??
     */
    @Test
    public void testUpdate() {
        Long pno = 10L;

        Product product = productRepository.selectOne(pno).get();
        
        product.changePname("10번 상품1");
        product.changedesc("10번 상품 설명입니다.");
        product.changePrice(5000);

//        product.clearList();
//
        product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE1.jpg");
        product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE2.jpg");
        product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE3.jpg");
//
        productRepository.save(product);
    }

    /**
     * Fetch Join 후 Select가 추가적으로 나가는 현상을 재현하기 위한 테스트 코드
     */
    @Test
    public void testUpdate2() {
        Long pno = 10L;

        Product product = productRepository.selectOne(pno).get();

        product.changePname("10번 상품1");
        product.changedesc("10번 상품 설명입니다.");
        product.changePrice(5000);
        productRepository.save(product);
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }
}