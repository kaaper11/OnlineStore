package mapper;

import dto.order.OrderDto;
import dto.product.response.ProductResponseDto;
import entity.order.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderMapper {

    public static OrderDto mapOrderToDto(Order order) {
        List<ProductResponseDto> productsDto = order.getProducts().stream()
                .map(ProductMapper::mapProductToDto)
                .toList();

        return new OrderDto(ClientMapper.mapClientToDto(order.getClient()), productsDto, order.getTotalPrice());
    }
}
