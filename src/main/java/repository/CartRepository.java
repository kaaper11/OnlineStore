package repository;

import entity.cart.Cart;

import java.util.*;

public class CartRepository {
    private final Set<Cart> carts = new HashSet<>();
    private Long idCounter = 0L;

    public Cart save(Cart cart) {
        carts.add(cart);
        return cart;
    }

    public Optional<Cart> delete(Long id) {
        return getCartById(id)
                .map(cart -> {
                    carts.remove(cart);
                    return cart;
                });
    }

    public Optional<Cart> update(Long id, Cart updatedCart) {
        return getCartById(id)
                .map(cart -> {
                    carts.remove(cart);
                    carts.add(updatedCart);
                    return cart;
                });
    }

    public Optional<Cart> getCartById(Long id) {
        return carts.stream()
                .filter(cart -> cart.getId().equals(id))
                .findFirst();
    }

    public Optional<Cart> getCartByClientId(Long clientId) {
        return carts.stream()
                .filter( cart -> cart.getClientId().equals(clientId))
                .findFirst();
    }

    public List<Cart> getAllCarts() {
        return carts.stream().toList();
    }

    public Long getNextId() {
        return idCounter++;
    }
}