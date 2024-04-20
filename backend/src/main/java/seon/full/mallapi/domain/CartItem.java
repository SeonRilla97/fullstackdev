package seon.full.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString(exclude = "cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tbl_cart_item",
        indexes = {
                //특정 상품이 특정 장바구니에 있는지 조회 기능을 위한 인덱스 설정
                @Index(columnList="cart_cno", name = "idx_certitem_cart"),
                @Index(columnList = "product_pno, cart_cno", name = "idx_cartitem_pno_cart")
        }
)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cino;

    @ManyToOne
    @JoinColumn(name = "product_pno")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_cno")
    private Cart cart;

    //수량
    private int qty;

    public void changeQty(int qty) {
        this.qty = qty;
    }

}
