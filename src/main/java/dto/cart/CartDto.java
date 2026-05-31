package dto.cart;

import dto.product.response.ProductResponseDto;

import java.util.List;

public record CartDto(Long clientId, List<ProductResponseDto> products) {
}
