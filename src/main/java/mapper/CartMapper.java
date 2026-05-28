package mapper;

import dto.cart.CartDto;
import dto.product.ProductDto;
import entity.cart.Cart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CartMapper {

    public static CartDto mapCartToDto(Cart cart) {
        List<ProductDto> productDtos = cart.getProducts().stream()
                .map(ProductMapper::mapProductToDto)
                .toList();

        return new CartDto(cart.getClientId(), productDtos);
    }

}
