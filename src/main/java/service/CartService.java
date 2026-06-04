package service;

import dto.cart.CartDto;
import dto.productconfig.ProductConfig;

public interface CartService {

    CartDto getCartById(Long id);

    CartDto getCartByClientId(Long clientId);

    CartDto addProductToCart(Long clientId, Long productId, ProductConfig productConfig);
}
