package repository;

import entity.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Order entities in memory.
 * It provides operations for saving and retrieving orders using a
 * thread-safe set and supports filtering by order or client identifiers.
 */
public class OrderRepository {
    private final Set<Order> orders = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    /**
     * Saves an order entity into the repository.
     *
     * @param order the order entity to be stored
     * @return the saved order instance
     */
    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    /**
     * Retrieves an order by its identifier.
     *
     * @param id the identifier of the order
     * @return an Optional containing the found order or empty if not found
     */
    public Optional<Order> getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves all orders belonging to a specific client.
     *
     * @param clientId the identifier of the client
     * @return a list of orders associated with the given client
     */
    public List<Order> getOneClientOrders(Long clientId) {
        return orders.stream()
                .filter(order -> order.getClient().getId().equals(clientId))
                .toList();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }
}
