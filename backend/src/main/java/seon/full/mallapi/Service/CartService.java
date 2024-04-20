package seon.full.mallapi.service;

import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.dto.CartItemDTO;
import seon.full.mallapi.dto.CartItemListDTO;

import java.util.List;

@Transactional
public interface CartService {

    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO);

    public List<CartItemListDTO> getCartItems(String email);

    public List<CartItemListDTO> remove(Long cino);

}
