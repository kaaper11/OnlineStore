package repository;

import entity.cart.Cart;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Cart entities in memory.
 * It provides operations for creating, retrieving, and deleting carts,
 * using a thread-safe set and an internal ID generator.
 */
public class CartRepository {
    private final Set<Cart> carts = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    public CartRepository() {
        save(new Cart(getNextId(), 100L, new ArrayList<>()));
    }

    /**
     * Creates and saves a new cart for the given client identifier.
     * A unique cart ID is generated automatically and the cart is initialized
     * with an empty product list.
     *
     * @param cart the identifier of the client for whom the cart is created
     * @return the newly created Cart instance
     */
    public Cart save(Cart cart) {
        carts.add(cart);
        return cart;
    }

    /**
     * Deletes a cart associated with the given client identifier.
     * If a matching cart is found, it is removed from the repository.
     *
     * @param clientId the identifier of the client whose cart should be deleted
     * @return an Optional containing the removed cart if found,
     * otherwise an empty Optional
     */
    public Optional<Cart> delete(Long clientId) {
        return getCartByClientId(clientId)
                .map(cart -> {
                    carts.remove(cart);
                    return cart;
                });
    }

    /**
     * Retrieves a cart by its unique identifier.
     *
     * @param id the identifier of the cart
     * @return an Optional containing the found cart or empty if not found
     */
    public Optional<Cart> getCartById(Long id) {
        return carts.stream()
                .filter(cart -> cart.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves a cart by the client identifier.
     *
     * @param clientId the identifier of the client
     * @return an Optional containing the found cart or empty if not found
     */
    public Optional<Cart> getCartByClientId(Long clientId) {
        return carts.stream()
                .filter(cart -> cart.getClientId().equals(clientId))
                .findFirst();
    }

    /**
     * Retrieves all carts stored in the repository.
     *
     * @return a list of all carts
     */
    public List<Cart> getAllCarts() {
        return carts.stream().toList();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }
}