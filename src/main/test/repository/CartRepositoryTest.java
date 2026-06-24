package repository;

import entity.cart.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {

    @InjectMocks
    private CartRepository repository;


    @Test
    void shouldSaveCartAndAssignId() {
        Cart cart = repository.save(new Cart(1L, 1L, new ArrayList<>()));

        assertNotNull(cart);
        assertEquals(1L, cart.getId());
        assertEquals(1L, cart.getClientId());
    }

    @Test
    void shouldIncrementIdForEachSavedCart() {
        Cart cart1 = repository.save(new Cart(1L, 1L, new ArrayList<>()));
        Cart cart2 = repository.save(new Cart(2L, 2L, new ArrayList<>()));

        assertEquals(1L, cart1.getId());
        assertEquals(2L, cart2.getId());
    }

    @Test
    void shouldFindCartById() {
        Cart cart = repository.save(new Cart(1L, 1L, new ArrayList<>()));

        Optional<Cart> found = repository.getCartById(cart.getId());

        assertTrue(found.isPresent());
        assertEquals(cart.getId(), found.get().getId());
    }

    @Test
    void shouldFindCartByClientId() {
        repository.save(new Cart(1L, 10L, new ArrayList<>()));

        Optional<Cart> found = repository.getCartByClientId(10L);

        assertTrue(found.isPresent());
        assertEquals(10L, found.get().getClientId());
    }

    @Test
    void shouldDeleteCartByClientId() {
        repository.save(new Cart(1L, 100L, new ArrayList<>()));

        Optional<Cart> deleted = repository.delete(100L);

        assertTrue(deleted.isPresent());
        assertEquals(100L, deleted.get().getClientId());

        assertTrue(repository.getCartByClientId(100L).isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenDeletingNonExistingCart() {
        Optional<Cart> deleted = repository.delete(999L);

        assertTrue(deleted.isEmpty());
    }

    @Test
    void shouldReturnAllCarts() {
        repository.save(new Cart(1L, 1L, new ArrayList<>()));
        repository.save(new Cart(2L, 2L, new ArrayList<>()));

        assertEquals(3, repository.getAllCarts().size());
        assertEquals(2L, repository.getAllCarts().get(1).getId());
    }

    @Test
    void shouldReturnEmptyWhenCartNotFoundById() {
        assertTrue(repository.getCartById(123L).isEmpty());
    }
}