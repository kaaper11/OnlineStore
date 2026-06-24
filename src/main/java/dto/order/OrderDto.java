package dto.order;

import dto.client.ClientResponseDto;
import dto.product.response.ProductResponseDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(ClientResponseDto client, List<ProductResponseDto> products, BigDecimal totalPrice) {
}
