package entity.product.config.computer;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Ram {
    GB8(BigDecimal.ZERO),
    GB16(new BigDecimal("300")),
    GB24(new BigDecimal("500"));

    private final BigDecimal price;

    Ram(BigDecimal price) {
        this.price = price;
    }
}
