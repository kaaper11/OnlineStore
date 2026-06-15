package entity.cart;

import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CartTest {

    private final Cart cart = new Cart(1L, 1L, new ArrayList<>());
    private final Electronics electronics = new Electronics(1L, "name", BigDecimal.TEN, 10);

    @Test
    public void shouldAddProductToCart() {
        Product product = cart.addProduct(electronics);

        assertThat(product).usingRecursiveComparison().isEqualTo(electronics);
        assertThat(cart.getProducts()).containsExactly(product);
    }

    @Test
    public void shouldProductListIsEmpty() {
        boolean result = cart.cheekProductsEmpty();

        assertThat(result).isTrue();
    }

    @Test
    public void shouldProductListIsNotEmpty() {
        cart.addProduct(electronics);

        boolean result = cart.cheekProductsEmpty();

        assertThat(result).isFalse();
    }
}
