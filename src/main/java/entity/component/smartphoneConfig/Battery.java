package entity.component.smartphoneConfig;

import entity.product.base.PricedComponent;

import java.math.BigDecimal;

public class Battery extends PricedComponent {
    private int capacity;

    public Battery(BigDecimal price, int capacity) {
        super(price);
        this.capacity = capacity;
    }
}
