package entity.product.config.smartphone;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Accessory {
    PHONE_CASE(new BigDecimal("50")),
    SCREEN_GLASS(new BigDecimal("20")),
    CHARGER(new BigDecimal("70")),
    POWERBANK(new BigDecimal("100"));

    private final BigDecimal price;
}
