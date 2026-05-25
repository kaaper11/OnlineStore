package entity.product.type;

import entity.product.base.Product;

import java.math.BigDecimal;

public class Electronics extends Product {
    public Electronics(Long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }
}
