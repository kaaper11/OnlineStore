package mapper;

import dto.order.OrderDto;
import dto.product.response.ProductResponseDto;
import entity.order.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Utility mapper class responsible for converting Order entities
 * into OrderDto objects.
 * This class is non-instantiable and provides static methods used
 * to transform order data into a transport-friendly format.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderMapper {

    public static OrderDto mapOrderToDto(Order order) {
        List<ProductResponseDto> productsDto = order.getProducts().stream()
                .map(ProductMapper::mapProductToDto)
                .toList();

        return new OrderDto(ClientMapper.mapClientToDto(order.getClient()), productsDto, order.getTotalPrice());
    }
}
