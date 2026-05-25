package entity.component.computerConfig;

import entity.product.base.StorageComponent;

import java.math.BigDecimal;

public class Rom extends StorageComponent {
    public Rom(BigDecimal price, int capacityGB) {
        super(price, capacityGB);
    }
}
