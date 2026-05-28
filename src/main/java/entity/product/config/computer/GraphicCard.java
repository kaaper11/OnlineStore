package entity.product.config.computer;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum GraphicCard {
    RTX5050(BigDecimal.ZERO),
    RTX5060(new BigDecimal("700")),
    RTX5070(new BigDecimal("1000")),
    RTX5090(new BigDecimal("2000"));

    private final BigDecimal price;

    GraphicCard(BigDecimal price) {
        this.price = price;
    }
}

