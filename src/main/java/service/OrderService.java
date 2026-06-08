package service;

import dto.order.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(Long clientId);

    OrderDto getOrderById(Long id);

    List<OrderDto> getOrdersByClientId(Long clientId);

    List<OrderDto> placeOrdersBatch(List<Long> clientIds);

}
