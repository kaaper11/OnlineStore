package service.cart;

import dto.cart.CartDto;
import dto.productconfig.ComputerConfig;
import dto.productconfig.ElectonicsConfig;
import entity.cart.Cart;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import entity.product.type.Electronics;
import exception.CartNotFoundException;
import exception.ElectronicsNotFoundException;
import exception.ProductOutOfStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CartRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class CartServiceImplTestIT {

    private CartService cartService;
    private ComputerRepository computerRepository;

    @BeforeEach
    void setUp() {
        CartRepository cartRepository = new CartRepository();
        computerRepository = new ComputerRepository();
        ElectronicsRepository electronicsRepository = new ElectronicsRepository();
        SmartphoneRepository smartphoneRepository = new SmartphoneRepository();

        cartService = new CartServiceImpl(cartRepository, computerRepository, smartphoneRepository,
                electronicsRepository);

        cartRepository.save(new Cart(1L, 1L, new ArrayList<>()));

        Computer computer = new Computer(1L, "name", new BigDecimal("100"), 20,
                Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);
        computerRepository.save(computer);


        Electronics electronics = new Electronics(2L, "Słuchawki", new BigDecimal("200"), 0);
        electronicsRepository.save(electronics);
    }

    @Test
    void shouldGetCartById() {
        CartDto dto = cartService.getCartById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.clientId()).isEqualTo(1L);
    }

    @Test
    void shouldGetCartByClientId() {
        CartDto dto = cartService.getCartByClientId(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.clientId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenCartNotFound() {
        assertThatExceptionOfType(CartNotFoundException.class).isThrownBy(() -> cartService.getCartById(1000L));
    }

    @Test
    void shouldAddProductToCart() {
        ComputerConfig computerConfig = new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8, Rom.GB500,
                GraphicCard.RTX5050);

        CartDto cart = cartService.addProductToCart("computer", 1L, 1L, computerConfig);

        assertThat(cart.products().size()).isEqualTo(1);
        assertThat(cart.products().getFirst().getId()).isEqualTo(1L);
    }

    @Test
    void shouldDecreaseQuantityAfterAddingToCart() {
        ComputerConfig config = new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

        cartService.addProductToCart("computer", 1L, 1L, config);

        Computer computer = computerRepository.getComputerById(1L).orElseThrow();
        assertThat(computer.getQuantity()).isEqualTo(19);
    }

    @Test
    void shouldThrowWhenProductOutOfStock() {
        assertThatExceptionOfType(ProductOutOfStockException.class)
                .isThrownBy(() -> cartService.addProductToCart("electronics", 1L, 2L, new ElectonicsConfig()));
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        assertThatExceptionOfType(ElectronicsNotFoundException.class)
                .isThrownBy(() -> cartService.addProductToCart("electronics",1L, 99L, new ElectonicsConfig()));
    }

    @Test
    void shouldThrowWhenCartNotFoundOnAddProduct() {
        assertThatExceptionOfType(CartNotFoundException.class)
                .isThrownBy(() -> cartService.addProductToCart("electronics", 99L, 1L, new ElectonicsConfig()));
    }
}
