package entity.product.config.computer;

import java.math.BigDecimal;

public enum Rom {
    GB500(BigDecimal.ZERO),
    GB1000(new BigDecimal("300")),
    GB2000(new BigDecimal("500"));

    private final BigDecimal price;

    Rom(BigDecimal price) {
        this.price = price;
    }
}
