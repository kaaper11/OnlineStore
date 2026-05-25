package entity.component.smartphoneConfig;

import java.math.BigDecimal;

public enum Accessory {
    PHONE_CASE(new BigDecimal("50")),
    SCREEN_GLASS(new BigDecimal("20")),
    CHARGER(new BigDecimal("70")),
    POWERBANK(new BigDecimal("100"));

    private BigDecimal price;

    Accessory(BigDecimal price) {
        this.price = price;
    }
}
