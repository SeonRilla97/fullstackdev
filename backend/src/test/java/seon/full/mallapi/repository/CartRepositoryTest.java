package seon.full.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.domain.Cart;
import seon.full.mallapi.domain.CartItem;
import seon.full.mallapi.domain.Member;
import seon.full.mallapi.domain.Product;
import seon.full.mallapi.dto.CartItemListDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Commit
    @Test
    public void testInsertByProdcut() {
        log.info("test1 ------------------");

        String email ="user1@aaa.com";
        Long pno = 5L;
        int qty = 1;

        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        if(cartItem != null){
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);

            return;
        }

        Optional<Cart> result = cartRepository.getCartOfMember(email);

        Cart cart =null;

        if(result.isEmpty()){
            log.info("MemberCart is not exist!!");

            Member member = Member.builder().email(email).build();

            Cart tempCart = Cart.builder().owner(member).build();

            cart = cartRepository.save(tempCart);
        }else{
            cart = result.get();
        }

        log.info(cart);


        if(cartItem == null) {
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).build();
        }

        cartItemRepository.save(cartItem);

    }


    @Test
    @Commit
    @Transactional
    public void testUpdateCino() {
    Long cino = 1L;
    int qty = 2;
        Optional<CartItem> result = cartItemRepository.findById(cino);

        CartItem cartItem = result.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);
    }

    @Test
    public void testDeleteThenList() {
        Long cino = 1L;

        //장바구니 번호
        Long cno = cartItemRepository.getCartFromItem(cino);

//        cartItemRepository.deleteById(cno);

        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        for (CartItemListDTO dto : cartItemList) {
            log.info(dto);
        }


    }
}