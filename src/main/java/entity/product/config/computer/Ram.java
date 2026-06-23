package entity.product.config.computer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Ram {
    GB8(BigDecimal.ZERO),
    GB16(new BigDecimal("300")),
    GB24(new BigDecimal("500"));

    private final BigDecimal price;
}
