package repository;

import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InvoiceRepositoryTest {

    @InjectMocks
    private InvoiceRepository invoiceRepository;

    private static final Client CLIENT = new Client(2L, "name", "email","pass", "123456789",
            new Address("Poland", "Warsaw", "Zlota", "15-820", 10), Role.USER);
    private static final Product PRODUCT = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private static final Order ORDER = new Order(1L, CLIENT, List.of(PRODUCT), BigDecimal.TEN);
    private static final Invoice INVOICE = new Invoice(1L, ORDER.getId(), CLIENT,  List.of(PRODUCT), BigDecimal.TEN,
            ZonedDateTime.now());

    @Test
    public void shouldSaveOrder() {
        Invoice result = invoiceRepository.save(INVOICE);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(INVOICE);
    }

    @Test
    public void shouldFindInvoiceByOrderId() {
        Invoice result = invoiceRepository.save(INVOICE);

        Optional<Invoice> invoiceResult = invoiceRepository.getInvoiceByOrderId(result.getId());
        assertThat(invoiceResult).isNotNull();
        assertThat(invoiceResult).usingRecursiveComparison().isEqualTo(Optional.of(INVOICE));
    }

    @Test
    public void shouldFindInvoicesByClientId() {
        invoiceRepository.save(INVOICE);

        List<Invoice> result = invoiceRepository.getInvoicesByClientId(CLIENT.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).isEqualTo(INVOICE);
    }
}
