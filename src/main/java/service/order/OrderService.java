package service.order;

import dto.order.OrderDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    OrderDto placeOrder(Long clientId);

    OrderDto getOrderById(Long id);

    List<OrderDto> getOrdersByClientId(Long clientId);

    List<OrderDto> placeSomeOrders(List<Long> clientIds);

    CompletableFuture<OrderDto> placeOrderAsync(Long clientId);
}
