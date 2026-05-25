package entity.product.base;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PricedComponent {
    private BigDecimal price;
}
