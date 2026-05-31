package entity.invoice;

import entity.client.Client;
import entity.product.type.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Invoice {
    private Long id;
    private Long orderId;
    private Client client;
    private List<Product> products;
    private BigDecimal totalPrice;
    private LocalDateTime invoiceDateTime;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
