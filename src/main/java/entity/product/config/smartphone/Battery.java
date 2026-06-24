package entity.product.config.smartphone;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Battery {
    MAH5000(BigDecimal.ZERO),
    MAH5500(new BigDecimal("200")),
    MAH6000(new BigDecimal("500"));

    private final BigDecimal price;
}
