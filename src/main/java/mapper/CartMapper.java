package mapper;

import dto.cart.CartDto;
import dto.product.response.ProductResponseDto;
import entity.cart.Cart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Utility mapper class responsible for converting Cart entities
 * into CartDto objects.
 * This class is non-instantiable and provides static mapping methods
 * used for transforming cart data into a transport-friendly format.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CartMapper {

    public static CartDto mapCartToDto(Cart cart) {
        List<ProductResponseDto> productDtos = cart.getProducts().stream()
                .map(ProductMapper::mapProductToDto)
                .toList();

        return new CartDto(cart.getClientId(), productDtos);
    }

}
