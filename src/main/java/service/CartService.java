package service;

import dto.cart.CartDto;

public interface CartService {
//    CartResponseDto createCart(CartRequestDto cartRequestDto);

//    CartResponseDto removeCart(Long id);

//    CartResponseDto updateCart(Long id, CartRequestDto cartRequestDto);

    CartDto getCartById(Long id);

    CartDto getCartByClientId(Long clientId);

    CartDto addProductToCart(Long clientId, Long productId);
}
