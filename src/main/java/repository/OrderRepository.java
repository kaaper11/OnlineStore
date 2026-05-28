package repository;

import entity.order.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderRepository {
    private final Set<Order> orders = new HashSet<>();
    private Long idCounter = 0L;

    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    public Optional<Order> getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    public List<Order> getOneClientOrders(Long clientId) {
        return orders.stream()
                .filter(order -> order.getClient().getId().equals(clientId))
                .toList();
    }

    public Long getNextId() {
        return idCounter++;
    }
}
