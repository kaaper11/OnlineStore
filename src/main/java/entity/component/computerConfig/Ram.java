package entity.component.computerConfig;

import java.math.BigDecimal;

public enum Ram {
    GB8(BigDecimal.ZERO),
    GB16(new BigDecimal("300")),
    GB24(new BigDecimal("500"));

    private BigDecimal price;

    Ram(BigDecimal price) {
        this.price = price;
    }
    }
