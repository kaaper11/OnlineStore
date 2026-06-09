package dto.invoice;

import dto.client.ClientResponseDto;
import dto.product.response.ProductResponseDto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record InvoiceDto(Long orderId, ClientResponseDto client, List<ProductResponseDto> products,
                         BigDecimal totalPrice, ZonedDateTime invoiceDateTime) {
}
