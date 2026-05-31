package repository;

import entity.client.Address;
import entity.client.Client;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InvoiceRepositoryTest {

    @InjectMocks
    private InvoiceRepository invoiceRepository;

    private final Client client = new Client(2L, "name", "email", "123456789",
            new Address("Poland", "Warsaw", "Zlota", "15-820", 10));
    private final Product product = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private final Order order = new Order(1L, client, List.of(product), BigDecimal.TEN);
    private final Invoice invoice = new Invoice(1L, order.getId(), client,  List.of(product), BigDecimal.TEN,
            LocalDateTime.now());

    @Test
    public void shouldSaveOrder() {
        Invoice result = invoiceRepository.save(invoice);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(invoice);
    }

    @Test
    public void shouldFindInvoiceByOrderId() {
        Invoice result = invoiceRepository.save(invoice);

        Optional<Invoice> invoiceResult = invoiceRepository.getInvoiceByOrderId(result.getId());
        assertThat(invoiceResult).isNotNull();
        assertThat(invoiceResult).usingRecursiveComparison().isEqualTo(Optional.of(invoice));
    }
}
