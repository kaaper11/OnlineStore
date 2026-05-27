package mapper;

import dto.cart.CartRequestDto;
import dto.cart.CartResponseDto;
import dto.product.ProductDto;
import entity.cart.Cart;
import entity.product.type.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CartMapper {

    public static Cart mapDtoToCart(CartRequestDto cartRequestDto, Long cartId) {
        return new Cart(cartId, cartRequestDto.clientId(), cartRequestDto.productIds());
    }

    public static CartResponseDto mapCartToCartResponseDto(Cart cart, List<Product> products) {
        List<ProductDto> productDtos = products.stream()
                .map(ProductMapper::mapProductToDto)
                .toList();

        return new CartResponseDto(cart.getClientId(), productDtos);
    }

}
