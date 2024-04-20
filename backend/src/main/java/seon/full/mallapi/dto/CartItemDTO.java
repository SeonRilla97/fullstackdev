package seon.full.mallapi.dto;

import lombok.Data;

/**
 * 장바구니에 상품 추가
 * 장바구니의 상품 수량 조정
 */
@Data
public class CartItemDTO {
    private String email;
    private Long pno;
    private int qty;
    private Long cino;
}
