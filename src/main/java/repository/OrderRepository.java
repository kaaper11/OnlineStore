package repository;

import entity.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class OrderRepository {
    private final Set<Order> orders = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

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
        return idCounter.getAndIncrement();
    }
}
