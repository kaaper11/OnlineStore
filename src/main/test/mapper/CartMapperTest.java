package mapper;

import dto.cart.CartDto;
import dto.product.response.ComputerResponseDto;
import dto.product.response.ElectronicsResponseDto;
import dto.product.response.ProductResponseDto;
import entity.cart.Cart;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CartMapperTest {

    private final Product product1 =
            new Electronics(1L, "Laptop", BigDecimal.valueOf(3000), 10);

    private final Product product2 =
            new Computer(2L, "Mouse", BigDecimal.valueOf(200), 20, Processor.INTEL_CORE_I3, Ram.GB8,
                    Rom.GB500, GraphicCard.RTX5050);

    private final ProductResponseDto productDto1 = new ElectronicsResponseDto(1L, "Laptop",
            BigDecimal.valueOf(3000), 10);
    private final ProductResponseDto productDto2 = new ComputerResponseDto(2L, "Mouse", BigDecimal.valueOf(200), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    @Test
    public void shouldMapCartToCartDto() {
        Cart cart = new Cart(1L, 1L, List.of(product1, product2));

        CartDto result = CartMapper.mapCartToDto(cart);
        CartDto expected = new CartDto(1L, List.of(productDto1, productDto2));

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("proc")
                .isEqualTo(expected);
    }
}