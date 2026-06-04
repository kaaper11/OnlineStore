package service;

import dto.cart.CartDto;
import dto.productconfig.ComputerConfig;
import entity.cart.Cart;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import exception.CartNotFoundException;
import exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CartRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import service.impl.CartServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ComputerRepository computerRepository;

    @Mock
    private SmartphoneRepository smartphoneRepository;

    @Mock
    private ElectronicsRepository electronicsRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void shouldReturnCartById() {
        Cart cart = new Cart(1L, 10L, new ArrayList<>());

        when(cartRepository.getCartById(1L))
                .thenReturn(Optional.of(cart));

        CartDto result = cartService.getCartById(1L);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(cart);
    }

    @Test
    void shouldThrowExceptionWhenCartByIdNotFound() {
        when(cartRepository.getCartById(1L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.getCartById(1L));
    }

    @Test
    void shouldReturnCartByClientId() {
        Cart cart = new Cart(1L, 10L, new ArrayList<>());

        when(cartRepository.getCartByClientId(10L)).thenReturn(Optional.of(cart));

        CartDto result = cartService.getCartByClientId(10L);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(cart);
    }

    @Test
    void shouldThrowExceptionWhenCartByClientIdNotFound() {
        when(cartRepository.getCartByClientId(10L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.getCartByClientId(10L));
    }

    @Test
    void shouldAddProductToCart() {
        Cart cart = new Cart(1L, 10L, new ArrayList<>());
        Computer product = new Computer(5L, "name", BigDecimal.ZERO, 10);

        when(cartRepository.getCartByClientId(10L)).thenReturn(Optional.of(cart));
        when(computerRepository.getComputerById(5L)).thenReturn(Optional.of(product));

        CartDto result = cartService.addProductToCart(10L, 5L,
                new ComputerConfig(Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050));

        assertNotNull(result);
        assertEquals(1, cart.getProducts().size());
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(cart);
    }

    @Test
    void shouldThrowExceptionWhenCartDoesNotExistWhileAddingProduct() {
        when(cartRepository.getCartByClientId(10L))
                .thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.addProductToCart(10L, 5L,
                new ComputerConfig(Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050)));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Cart cart = new Cart(1L, 10L, new ArrayList<>());

        when(cartRepository.getCartByClientId(anyLong())).thenReturn(Optional.of(cart));

        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.empty());

        when(smartphoneRepository.getSmartphoneById(anyLong())).thenReturn(Optional.empty());

        when(electronicsRepository.getElectronicsById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartService.addProductToCart(10L, 5L,
                new ComputerConfig(Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050)));
    }
}