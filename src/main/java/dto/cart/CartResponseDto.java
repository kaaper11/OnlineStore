package dto.cart;

import dto.product.ProductDto;

import java.util.List;

public record CartResponseDto(Long clientId, List<ProductDto> products) {
}
