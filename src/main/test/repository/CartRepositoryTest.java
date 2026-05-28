package repository;

import entity.cart.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {

    @InjectMocks
    private CartRepository repository;


    @Test
    void shouldSaveCartAndAssignId() {
        Cart cart = repository.save(1L);

        assertNotNull(cart);
        assertEquals(0L, cart.getId());
        assertEquals(1L, cart.getClientId());
    }

    @Test
    void shouldIncrementIdForEachSavedCart() {
        Cart cart1 = repository.save(1L);
        Cart cart2 = repository.save(2L);

        assertEquals(0L, cart1.getId());
        assertEquals(1L, cart2.getId());
    }

    @Test
    void shouldFindCartById() {
        Cart cart = repository.save(1L);

        Optional<Cart> found = repository.getCartById(cart.getId());

        assertTrue(found.isPresent());
        assertEquals(cart.getId(), found.get().getId());
    }

    @Test
    void shouldFindCartByClientId() {
        repository.save(10L);

        Optional<Cart> found = repository.getCartByClientId(10L);

        assertTrue(found.isPresent());
        assertEquals(10L, found.get().getClientId());
    }

    @Test
    void shouldDeleteCartByClientId() {
        repository.save(100L);

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
        repository.save(1L);
        repository.save(2L);

        assertEquals(2, repository.getAllCarts().size());
    }

    @Test
    void shouldReturnEmptyWhenCartNotFoundById() {
        assertTrue(repository.getCartById(123L).isEmpty());
    }
}