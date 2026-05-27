package repository;

import entity.cart.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    @InjectMocks
    private CartRepository cartRepository;

    private final Cart cart = new Cart(1L, 100L, List.of(1L, 2L));
    private final Cart updatedCart = new Cart(1L, 200L, List.of(3L, 4L));


    @Test
    public void shouldSaveCart() {
        Cart result = cartRepository.save(cart);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(cart);

        assertThat(cartRepository.getCartById(1L))
                .contains(cart);
    }

    @Test
    public void shouldDeleteCart() {
        cartRepository.save(cart);

        Optional<Cart> result = cartRepository.delete(1L);

        assertThat(result)
                .contains(cart);

        assertThat(cartRepository.getCartById(1L))
                .isEmpty();
    }

    @Test
    public void shouldReturnEmptyWhenDeletingNonExistingCart() {
        Optional<Cart> result = cartRepository.delete(99L);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldUpdateCart() {
        cartRepository.save(cart);

        Optional<Cart> result = cartRepository.update(1L, updatedCart);

        assertThat(result)
                .contains(cart);

        assertThat(cartRepository.getCartById(1L))
                .contains(updatedCart);
    }

    @Test
    public void shouldReturnEmptyWhenUpdatingNonExistingCart() {
        Optional<Cart> result = cartRepository.update(99L, updatedCart);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGetCartById() {
        cartRepository.save(cart);

        Optional<Cart> result = cartRepository.getCartById(1L);

        assertThat(result)
                .contains(cart);
    }

    @Test
    public void shouldGetCartByClientId() {
        cartRepository.save(cart);

        Optional<Cart> result = cartRepository.getCartByClientId(100L);

        assertThat(result)
                .contains(cart);
    }

    @Test
    public void shouldReturnAllCarts() {
        cartRepository.save(cart);
        cartRepository.save(updatedCart);

        List<Cart> result = cartRepository.getAllCarts();

        assertThat(result)
                .hasSize(2)
                .contains(cart, updatedCart);
    }

    @Test
    public void shouldGenerateNextId() {
        Long firstId = cartRepository.getNextId();
        Long secondId = cartRepository.getNextId();

        assertThat(firstId).isEqualTo(0L);
        assertThat(secondId).isEqualTo(1L);
    }
}