package dto.cart;

import dto.product.ProductDto;

import java.util.List;

public record CartDto(Long clientId, List<ProductDto> products) {
}
