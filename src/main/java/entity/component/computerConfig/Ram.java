package entity.component.computerConfig;

import entity.product.base.StorageComponent;

import java.math.BigDecimal;

public class Ram extends StorageComponent {
    public Ram(BigDecimal price, int capacityGB) {
        super(price, capacityGB);
    }
}
