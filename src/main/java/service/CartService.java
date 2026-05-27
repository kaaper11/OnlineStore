package service;

import dto.cart.CartRequestDto;
import dto.cart.CartResponseDto;

public interface CartService {
    CartResponseDto createCart(CartRequestDto cartRequestDto);

    CartResponseDto removeCart(Long id);

    CartResponseDto updateCart(Long id, CartRequestDto cartRequestDto);

    CartResponseDto getCartById(Long id);

    CartResponseDto getCartByClientId(Long clientId);
}
