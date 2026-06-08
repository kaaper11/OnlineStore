package entity.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Discount {
    private Long id;
    private Long productId;
    private DiscountType discountType;
    private BigDecimal value;
}
