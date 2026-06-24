package mapper;

import dto.invoice.InvoiceDto;
import dto.product.response.ProductResponseDto;
import entity.invoice.Invoice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Utility mapper class responsible for converting Invoice entities
 * into InvoiceDto objects.
 * This class is non-instantiable and provides static methods used
 * to transform invoice data into a transport-friendly format.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InvoiceMapper {

    public static InvoiceDto mapInvoiceToDto(Invoice invoice) {

        List<ProductResponseDto> productsDto = invoice.getProducts().stream()
                .map(ProductMapper::mapProductToDto)
                .toList();

        return new InvoiceDto(invoice.getOrderId(), ClientMapper.mapClientToDto(invoice.getClient()), productsDto,
                invoice.getTotalPrice(), invoice.getInvoiceDateTime());
    }
}
