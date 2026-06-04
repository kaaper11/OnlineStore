package entity.product.type;

import java.math.BigDecimal;

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
        return new Electronics(getId(), getName(), getPrice(), getQuantity());
    }
}
