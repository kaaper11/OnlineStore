package repository;

import entity.cart.Cart;

import java.util.*;

public class CartRepository {
    private final Set<Cart> carts = new HashSet<>();
    private Long idCounter = 0L;

    public Cart save(Long clientId) {
        Cart cart = new Cart(getNextId(), clientId, new ArrayList<>());
        carts.add(cart);
        return cart;
    }

    public Optional<Cart> delete(Long clientId) {
        return getCartByClientId(clientId)
                .map(cart -> {
                    carts.remove(cart);
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
                .filter(cart -> cart.getClientId().equals(clientId))
                .findFirst();
    }

    public List<Cart> getAllCarts() {
        return carts.stream().toList();
    }

    public Long getNextId() {
        return idCounter++;
    }
}