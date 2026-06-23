package entity.product.type;

import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents a basic electronics product in the system.
 * This class extends Product and does not include additional configuration,
 * meaning its total price is equal to its base price.
 * <p>
 * It is used for simple electronic items that do not require component-based configuration.
 */
@SuperBuilder(toBuilder = true)
public class Electronics extends Product {
    public Electronics(Long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getPrice();
    }

    @Override
    public Product getProductCopy() {
        return this.toBuilder().build();
    }
}
