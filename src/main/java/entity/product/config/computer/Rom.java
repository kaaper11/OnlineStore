package entity.product.config.computer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Rom {
    GB500(BigDecimal.ZERO),
    GB1000(new BigDecimal("300")),
    GB2000(new BigDecimal("500"));

    private final BigDecimal price;
}
