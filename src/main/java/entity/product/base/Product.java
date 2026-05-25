package entity.product.base;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public abstract class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
