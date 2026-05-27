package service;

import dto.cart.CartRequestDto;
import dto.cart.CartResponseDto;
import entity.cart.Cart;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import entity.product.type.Electronics;
import exception.CartNotFoundException;
import exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CartRepository;
import repository.ComputerRepository;
import repository.ElectronicsRepository;
import repository.SmartphoneRepository;
import service.impl.CartServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

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

    private final CartRequestDto request = new CartRequestDto(1L, List.of(10L, 20L));

    private final Cart cart = new Cart(1L, 1L, List.of(10L, 20L));

    private final Electronics product1 = new Electronics(10L, "Laptop", BigDecimal.valueOf(3000), 10);

    private final Computer product2 = new Computer(20L, "Mouse", BigDecimal.valueOf(200), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);


    @Test
    public void shouldCreateCart() {
        when(cartRepository.getNextId()).thenReturn(1L);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        when(computerRepository.getComputerById(10L)).thenReturn(Optional.of(product2));
        when(electronicsRepository.getElectronicsById(20L)).thenReturn(Optional.of(product1));

        CartResponseDto result = cartService.createCart(request);

        assertThat(result).isNotNull();

        verify(cartRepository).save(any(Cart.class));
        verify(cartRepository).getNextId();
    }

    @Test
    public void shouldRemoveCartWhenCartIsInRepository() {
        when(cartRepository.delete(anyLong())).thenReturn(Optional.of(cart));
        when(computerRepository.getComputerById(10L)).thenReturn(Optional.of(product2));
        when(electronicsRepository.getElectronicsById(20L)).thenReturn(Optional.of(product1));

        CartResponseDto result = cartService.removeCart(1L);

        assertThat(result).isNotNull();
        verify(cartRepository).delete(anyLong());
        assertThat(result.clientId()).isEqualTo(cart.getClientId());
    }

    @Test
    public void shouldThrowWhenCartNotFoundOnDelete() {
        when(cartRepository.delete(1L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class,
                () -> cartService.removeCart(1L));
    }

    @Test
    public void shouldUpdateCartWhenCartIsInRepository() {
        when(cartRepository.update(anyLong(), any(Cart.class))).thenReturn(Optional.of(cart));
        when(computerRepository.getComputerById(10L)).thenReturn(Optional.of(product2));
        when(electronicsRepository.getElectronicsById(20L)).thenReturn(Optional.of(product1));

        CartResponseDto result = cartService.updateCart(1L, request);

        assertThat(result).isNotNull();
        verify(cartRepository).update(anyLong(), any(Cart.class));
        assertThat(result.clientId()).isEqualTo(cart.getClientId());
    }

    @Test
    public void shouldThrowWhenCartNotFoundOnUpdate() {
        when(cartRepository.update(eq(1L), any())).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class,
                () -> cartService.updateCart(1L, request));
    }

    @Test
    public void shouldGetCartByIdWhenCartIsInRepository() {
        when(cartRepository.getCartById(anyLong())).thenReturn(Optional.of(cart));
        when(computerRepository.getComputerById(10L)).thenReturn(Optional.of(product2));
        when(electronicsRepository.getElectronicsById(20L)).thenReturn(Optional.of(product1));

        CartResponseDto result = cartService.getCartById(1L);

        assertThat(result).isNotNull();
        verify(cartRepository).getCartById(anyLong());
        assertThat(result.clientId()).isEqualTo(cart.getClientId());
    }

    @Test
    public void shouldThrowWhenCartNotFoundOnGetById() {
        when(cartRepository.getCartById(1L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class,
                () -> cartService.getCartById(1L));
    }

    @Test
    public void shouldThrowWhenCartNotFoundOnGetByClientId() {
        when(cartRepository.getCartByClientId(1L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class,
                () -> cartService.getCartByClientId(1L));
    }

    @Test
    public void shouldAddProductToCart() {
        List<Long> productIds = new ArrayList<>();
        productIds.add(20L);
        Cart cartLocal = new Cart(1L, 1L, productIds);

        when(cartRepository.getCartByClientId(1L)).thenReturn(Optional.of(cartLocal));

        when(computerRepository.getComputerById(10L)).thenReturn(Optional.of(product2));
        when(electronicsRepository.getElectronicsById(20L)).thenReturn(Optional.of(product1));

        CartResponseDto result = cartService.addProductToCart(1L, 10L);

        assertThat(result).isNotNull();
        verify(cartRepository).getCartByClientId(1L);
        assertThat(cart.getProductsIds()).contains(10L);
        assertThat(result.products()).hasSize(2);
    }

    @Test
    public void shouldThrowWhenCartNotFound() {
        when(cartRepository.getCartByClientId(anyLong())).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class,
                () -> cartService.addProductToCart(1L, 2L));
    }

    @Test
    public void shouldThrowWhenAddingNonExistingProductToCart() {
        when(cartRepository.getCartByClientId(anyLong())).thenReturn(Optional.of(cart));

        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.empty());
        when(electronicsRepository.getElectronicsById(anyLong())).thenReturn(Optional.empty());
        when(smartphoneRepository.getSmartphoneById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> cartService.addProductToCart(1L, 10L));

    }
}