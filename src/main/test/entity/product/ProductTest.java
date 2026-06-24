package entity.product;

import entity.product.type.Electronics;
import entity.product.type.Product;
import exception.ProductOutOfStockException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @Test
    public void shouldDecreaseQuantityWithMoreThanZeroQuantity() {
        Product product = new Electronics(1L, "name", BigDecimal.ONE, 10);

        int quantity = product.decreaseQuantity();

        assertThat(quantity).isEqualTo(product.getQuantity());
    }

    @Test
    public void shouldThrowExceptionWhenDecreaseQuantityWithZeroQuantity() {
        Product product = new Electronics(1L, "name", BigDecimal.ZERO, 0);

        assertThrows(ProductOutOfStockException.class, product::decreaseQuantity);
    }
}
