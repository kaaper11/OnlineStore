package entity.invoice;

import entity.client.Client;
import entity.product.type.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents an invoice generated for an order.
 * The invoice contains information about the client, ordered products,
 * total price, and the date and time when the invoice was issued.
 * <p>
 * Equality of invoices is based on the invoice identifier (id).
 */
@AllArgsConstructor
@Getter
public class Invoice {
    private Long id;
    private Long orderId;
    private Client client;
    private List<Product> products;
    private BigDecimal totalPrice;
    private ZonedDateTime invoiceDateTime;

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

    public Invoice invoiceCopyWithLocalTime(ZonedDateTime localDateTime) {
        return new Invoice(this.id, this.orderId, this.client, this.products, this.totalPrice, localDateTime);
    }
}
