package entity;

import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ElectronicsTest {

    @Test
    public void shouldReturnElectronicsCopyCorrectly() {
        Electronics electronics = new Electronics(1L, "name", BigDecimal.ONE, 10);

        Product computer1 = electronics.getProductCopy();

        assertThat(computer1).isNotNull();
        assertThat(computer1.getName()).isEqualTo(electronics.getName());
    }
}
