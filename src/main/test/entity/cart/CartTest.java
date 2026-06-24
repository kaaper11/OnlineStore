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

    private final static Cart CART = new Cart(1L, 1L, new ArrayList<>());
    private final static Electronics NAME = new Electronics(1L, "name", BigDecimal.TEN, 10);

    @Test
    public void shouldAddProductToCart() {
        Product product = CART.addProduct(NAME);

        assertThat(product).usingRecursiveComparison().isEqualTo(NAME);
        assertThat(CART.getProducts()).containsExactly(product);
    }

    @Test
    public void shouldProductListIsEmpty() {
        boolean result = CART.isEmpty();

        assertThat(result).isTrue();
    }

    @Test
    public void shouldProductListIsNotEmpty() {
        CART.addProduct(NAME);

        boolean result = CART.isEmpty();

        assertThat(result).isFalse();
    }
}
