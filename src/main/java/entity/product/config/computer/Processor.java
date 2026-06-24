package entity.product.config.computer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Processor {
    INTEL_CORE_I3(BigDecimal.ZERO),
    INTEL_CORE_I5(new BigDecimal("500")),
    INTEL_CORE_I7(new BigDecimal("1000"));

    private final BigDecimal price;
}
