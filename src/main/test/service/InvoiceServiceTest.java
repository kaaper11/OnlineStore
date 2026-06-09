package service;

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
import service.impl.InvoiceServiceImpl;

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
public class InvoiceServiceTest {

    @InjectMocks
    InvoiceServiceImpl invoiceService;

    @Mock
    InvoiceRepository invoiceRepository;

    private final Product product = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private final Cart cart = new Cart(1L, 10L, List.of(product));
    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);
    private final Client client = new Client(1L, "name", "name@test.com", "pass", "123456789",
            address, Role.USER);
    private final Order order = new Order(1L, client, cart.getProducts(), BigDecimal.TEN);
    private final Invoice invoice = new Invoice(1L, order.getId(), client, order.getProducts(), BigDecimal.TEN,
            ZonedDateTime.now());

    @Test
    void shouldFindInvoiceByOrderId() {
        when(invoiceRepository.getInvoiceByOrderId(anyLong())).thenReturn(Optional.of(invoice));

        InvoiceDto invoiceDto = invoiceService.getInvoiceByOrderId(1L);

        assertThat(invoiceDto).isNotNull();
        assertThat(invoiceDto.totalPrice()).isEqualTo(invoice.getTotalPrice());
    }

    @Test
    void shouldThrowExceptionWhenInvoiceNotFound() {
        when(invoiceRepository.getInvoiceByOrderId(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvoiceNotFoundException.class, () -> invoiceService.getInvoiceByOrderId(1L));
    }

    @Test
    void shouldFindInvoicesByClientId() {
        when(invoiceRepository.getInvoicesByClientId(anyLong())).thenReturn(List.of(invoice));

        List<InvoiceDto> invoicesDto = invoiceService.getInvoicesByClientId(1L, ZoneId.systemDefault());

        assertThat(invoicesDto).hasSize(1);
        assertThat(invoicesDto.getFirst()).usingRecursiveComparison().isEqualTo(invoice);
    }
}
