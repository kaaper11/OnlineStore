package dto.order;

import dto.client.ClientDto;
import dto.product.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(ClientDto client, List<ProductDto> products, BigDecimal totalPrice) {
}
