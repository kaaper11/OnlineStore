package entity.product.base;

import java.math.BigDecimal;

public abstract class StorageComponent extends PricedComponent {
    private int capacityGB;

    protected StorageComponent(BigDecimal price, int capacityGB) {
        super(price);
        this.capacityGB = capacityGB;
    }
}
