package service.cart;

import dto.cart.CartDto;
import dto.productconfig.ProductConfig;

public interface CartService {

    CartDto getCartById(Long id);

    CartDto getCartByClientId(Long clientId);

    CartDto addProductToCart(String type, Long clientId, Long productId, ProductConfig productConfig);
}
