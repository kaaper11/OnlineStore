package entity.product.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import exception.ProductOutOfStockException;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Abstract base class representing a product in the system.
 * A product contains common attributes such as id, name, price, and quantity,
 * and defines shared behavior for all specific product types.
 * <p>
 * Equality of products is based on the product identifier (id).
 */
@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Product {
    private Long id;
    private String name;
    @Setter
    private BigDecimal price;
    @Setter
    private int quantity;

    /**
     * Decreases the available quantity of the product by one.
     * If the product is out of stock (quantity equals zero), a
     * ProductOutOfStockException is thrown.
     *
     * @return the updated quantity after the purchase operation
     * @throws ProductOutOfStockException if the product is not available in stock
     */
    public int buyProduct() {
        if (quantity == 0) {
            throw new ProductOutOfStockException(this);
        }
        quantity--;
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public abstract BigDecimal getTotalPrice();

    @JsonIgnore
    public abstract Product getProductCopy();
}
