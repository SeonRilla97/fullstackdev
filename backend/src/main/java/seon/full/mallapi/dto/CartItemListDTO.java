package seon.full.mallapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;
    private int qty;
    private Long pno;
    private String pname;
    private int price;
    private String imageFile;

    //JPA의 DTO 반환을 위한 생성자
    public CartItemListDTO(Long cino, int qty, Long pno, String pname, int price, String imageFile) {
        this.cino = cino;
        this.qty = qty;
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.imageFile = imageFile;
    }
}
