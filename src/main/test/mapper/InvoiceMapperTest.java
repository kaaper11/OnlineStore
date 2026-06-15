package mapper;

import dto.invoice.InvoiceDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InvoiceMapperTest {

    @Test
    public void shouldMapInvoiceToDto() {
        Client client = new Client(2L, "name", "email","pass", "123456789",
                new Address("Poland", "Warsaw", "Zlota", "15-820", 10), Role.USER);
        Product product = new Electronics(1L, "product", BigDecimal.TEN, 10);
        Order order = new Order(1L, client, List.of(product), BigDecimal.TEN);
        Invoice invoice = new Invoice(1L, order.getId(), client, List.of(product), BigDecimal.TEN,
                ZonedDateTime.now());

        InvoiceDto invoiceDto = InvoiceMapper.mapInvoiceToDto(invoice);

        assertThat(invoiceDto).isNotNull();
        assertThat(invoiceDto.client().name()).isEqualTo(order.getClient().getName());
    }
}
