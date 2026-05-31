package dto.invoice;

import dto.client.ClientResponseDto;
import dto.product.response.ProductResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceDto(Long orderId, ClientResponseDto client, List<ProductResponseDto> products,
                         BigDecimal totalPrice, LocalDateTime invoiceDateTime) {
}
