package entity.component.computerConfig;

import entity.product.base.NamedComponent;

import java.math.BigDecimal;

public class Processor extends NamedComponent {
    private double clockSpeedGHz;

    public Processor(BigDecimal price, String name, double clockSpeedGHz) {
        super(price, name);
        this.clockSpeedGHz = clockSpeedGHz;
    }
}
