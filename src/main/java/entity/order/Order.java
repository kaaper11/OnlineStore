package entity.order;

import entity.client.Client;
import entity.product.type.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer order in the system.
 * An order contains information about the client who placed it,
 * the list of products included in the order, and the total price.
 * <p>
 * Equality of orders is based on the order identifier (id).
 */
@AllArgsConstructor
@Getter
public class Order {
    private Long id;
    private Client client;
    private List<Product> products;
    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
