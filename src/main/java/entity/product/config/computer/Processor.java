package entity.product.config.computer;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Processor {
    INTEL_CORE_I3(BigDecimal.ZERO),
    INTEL_CORE_I5(new BigDecimal("500")),
    INTEL_CORE_I7(new BigDecimal("1000"));

    private final BigDecimal price;

    Processor(BigDecimal price) {
        this.price = price;
    }
}
