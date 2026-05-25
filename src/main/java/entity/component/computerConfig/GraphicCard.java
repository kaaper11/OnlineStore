package entity.component.computerConfig;

import java.math.BigDecimal;

public enum GraphicCard {
    RTX5050(BigDecimal.ZERO),
    RTX5060(new BigDecimal("700")),
    RTX5070(new BigDecimal("1000")),
    RTX5090(new BigDecimal("2000"));

    private BigDecimal price;

    GraphicCard(BigDecimal price) {
        this.price = price;
    }
}

