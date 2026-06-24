package service.invoice;

import dto.invoice.InvoiceDto;
import entity.cart.Cart;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import exception.InvoiceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {

    @InjectMocks
    InvoiceServiceImpl invoiceService;

    @Mock
    InvoiceRepository invoiceRepository;

    private static final Product PRODUCT = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private static final Cart CART = new Cart(1L, 10L, List.of(PRODUCT));
    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);
    private static final Client CLIENT = new Client(1L, "name", "name@test.com", "pass", "123456789",
            ADDRESS, Role.USER);
    private static final Order ORDER = new Order(1L, CLIENT, CART.getProducts(), BigDecimal.TEN);
    private static final Invoice INVOICE = new Invoice(1L, ORDER.getId(), CLIENT, ORDER.getProducts(), BigDecimal.TEN,
            ZonedDateTime.now());

    @Test
    void shouldFindInvoiceByOrderId() {
        when(invoiceRepository.getInvoiceByOrderId(anyLong())).thenReturn(Optional.of(INVOICE));

        InvoiceDto invoiceDto = invoiceService.getInvoiceByOrderId(1L);

        assertThat(invoiceDto).isNotNull();
        assertThat(invoiceDto.totalPrice()).isEqualTo(INVOICE.getTotalPrice());
    }

    @Test
    void shouldThrowExceptionWhenInvoiceNotFound() {
        when(invoiceRepository.getInvoiceByOrderId(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvoiceNotFoundException.class, () -> invoiceService.getInvoiceByOrderId(1L));
    }

    @Test
    void shouldFindInvoicesByClientId() {
        when(invoiceRepository.getInvoicesByClientId(anyLong())).thenReturn(List.of(INVOICE));

        List<InvoiceDto> invoicesDto = invoiceService.getInvoicesByClientId(1L, ZoneId.systemDefault());

        assertThat(invoicesDto).hasSize(1);
        assertThat(invoicesDto.getFirst()).usingRecursiveComparison().isEqualTo(INVOICE);
    }
}
