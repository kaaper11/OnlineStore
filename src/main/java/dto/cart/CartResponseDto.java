package dto.cart;

import dto.product.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDto(Long clientId, List<ProductDto> products, BigDecimal totalPrice) {
}
