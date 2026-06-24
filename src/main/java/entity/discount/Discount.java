package entity.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents a discount applied to a specific product.
 * A discount contains information about the product it applies to,
 * the type of discount, and its value.
 */
@AllArgsConstructor
@Getter
public class Discount {
    @Setter
    private Long id;
    private Long productId;
    private DiscountType discountType;
    private BigDecimal value;
}
