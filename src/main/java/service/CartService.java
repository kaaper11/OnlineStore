package service;

import dto.cart.CartDto;

public interface CartService {

    CartDto getCartById(Long id);

    CartDto getCartByClientId(Long clientId);

    CartDto addProductToCart(Long clientId, Long productId);
}
