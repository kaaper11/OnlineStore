package entity.product.base;

import java.math.BigDecimal;

public abstract class NamedComponent extends PricedComponent {
    private String name;

    protected NamedComponent(BigDecimal price, String name) {
        super(price);
        this.name = name;
    }
}
