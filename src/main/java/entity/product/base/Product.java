package entity.product.base;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
