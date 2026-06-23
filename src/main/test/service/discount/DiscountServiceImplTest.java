package service.discount;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.client.Client;
import entity.client.Role;
import entity.discount.Discount;
import entity.discount.DiscountType;
import entity.product.type.Computer;
import entity.product.type.Product;
import exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;
import repository.DiscountRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    @Mock private DiscountRepository discountRepository;
    @Mock private ComputerRepository computerRepository;
    @Mock private SmartphoneRepository smartphoneRepository;
    @Mock private ElectronicsRepository electronicsRepository;
    @Mock private ClientRepository clientRepository;

    @InjectMocks
    private DiscountServiceImpl service;

    @Test
    void shouldAddDiscountSuccessfully() {
        Long clientId = 1L;

        Client admin = mock(Client.class);
        when(admin.getRole()).thenReturn(Role.ADMIN);

        when(clientRepository.getClientById(clientId))
                .thenReturn(Optional.of(admin));

        DiscountRequest request = new DiscountRequest(100L, DiscountType.PERCENT, BigDecimal.valueOf(10));

        when(discountRepository.exists(100L)).thenReturn(false);
        when(discountRepository.getNextId()).thenReturn(1L);

        Computer computer = new Computer(5L, "name", BigDecimal.ZERO, 10);

        when(computerRepository.getComputerById(100L)).thenReturn(Optional.of(computer));

        Discount discount = new Discount(
                1L,
                100L,
                DiscountType.PERCENT,
                BigDecimal.valueOf(10)
        );

        when(discountRepository.save(any())).thenReturn(discount);

        DiscountDto result = service.addDiscount("computer", request, clientId);

        assertNotNull(result);
        assertEquals(discount.getDiscountType(), result.discountType());
        verify(discountRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenClientNotAdmin() {
        Long clientId = 1L;

        Client user = mock(Client.class);
        when(user.getRole()).thenReturn(Role.USER);

        when(clientRepository.getClientById(clientId)).thenReturn(Optional.of(user));

        DiscountRequest request = new DiscountRequest(100L, DiscountType.PERCENT, BigDecimal.valueOf(10));

        assertThrows(NoPermissionsException.class, () -> service.addDiscount("computer", request, clientId));
    }

    @Test
    void shouldThrowWhenDiscountAlreadyExists() {
        Long clientId = 1L;

        Client admin = mock(Client.class);
        when(admin.getRole()).thenReturn(Role.ADMIN);

        when(clientRepository.getClientById(clientId)).thenReturn(Optional.of(admin));

        DiscountRequest request = new DiscountRequest(100L, DiscountType.PERCENT, BigDecimal.valueOf(10));

        when(discountRepository.exists(100L)).thenReturn(true);

        assertThrows(DiscountForProductAlreadyExists.class, () -> service.addDiscount("computer", request, clientId));
    }

    @Test
    void shouldApplyPercentDiscount() {
        Discount discount = new Discount(1L, 100L, DiscountType.PERCENT, BigDecimal.valueOf(10));

        BigDecimal price = BigDecimal.valueOf(100);

        BigDecimal result = service.applyDiscount(price, discount);

        assertEquals(BigDecimal.valueOf(90.0), result);
    }

    @Test
    void shouldApplyConstantDiscount() {
        Discount discount = new Discount(1L, 100L, DiscountType.CONSTANT, BigDecimal.valueOf(20));

        BigDecimal result = service.applyDiscount(BigDecimal.valueOf(100), discount);

        assertEquals(BigDecimal.valueOf(80), result);
    }

    @Test
    void shouldReturnDiscountForProduct() {
        Discount discount = new Discount(1L, 100L, DiscountType.PERCENT, BigDecimal.TEN);

        when(discountRepository.getByProductId(100L)).thenReturn(Optional.of(discount));

        Optional<DiscountDto> result = service.getDiscountForProduct(100L);

        assertEquals(100L, result.get().productId());
    }

    @Test
    void shouldCalculateDiscountedPriceIfExists() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getTotalPrice()).thenReturn(BigDecimal.valueOf(100));

        Discount discount = new Discount(1L, 1L, DiscountType.CONSTANT, BigDecimal.valueOf(20));

        when(discountRepository.getByProductId(1L)).thenReturn(Optional.of(discount));

        BigDecimal result = service.calculateDiscount(product);

        assertEquals(BigDecimal.valueOf(80), result);
    }

    @Test
    void shouldReturnOriginalPriceWhenNoDiscount() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getTotalPrice()).thenReturn(BigDecimal.valueOf(100));

        when(discountRepository.getByProductId(1L)).thenReturn(Optional.empty());

        BigDecimal result = service.calculateDiscount(product);

        assertEquals(BigDecimal.valueOf(100), result);
    }


    @Test
    void shouldCalculateTotalCart() {
        Product p1 = mock(Product.class);
        Product p2 = mock(Product.class);

        when(p1.getId()).thenReturn(1L);
        when(p1.getTotalPrice()).thenReturn(BigDecimal.valueOf(100));

        when(p2.getId()).thenReturn(2L);
        when(p2.getTotalPrice()).thenReturn(BigDecimal.valueOf(200));

        when(discountRepository.getByProductId(anyLong())).thenReturn(Optional.empty());

        BigDecimal result = service.calculateTotalCart(List.of(p1, p2));

        assertEquals(BigDecimal.valueOf(300), result);
    }
}