package seon.full.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    @ElementCollection //값 타입 객체를 컬렉션으로 처리하기 위해 선언 - @Embeddable 사용해야함
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changePname(String pname) {
        this.pname = pname;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changedesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * ProductImage 를 통한 이미지 추가
     * @param image
     */
    public void addImage(ProductImage image) {
        image.setOrd(this.imageList.size());
        imageList.add(image);
    }

    /**
     * 파일 명으로 이미지 추가
     * @param fileName
     */
    public void addImageString (String fileName) {
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();
        addImage(productImage);
    }

    /**
     * 리스트 초기화
     */
    public void clearList() {
        this.imageList.clear();
    }
}
