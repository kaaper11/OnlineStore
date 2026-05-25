package entity.component.smartphoneConfig;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Battery {
    MAH5000(BigDecimal.ZERO),
    MAH5500(new BigDecimal("200")),
    MAH6000(new BigDecimal("500"));

    private BigDecimal price;

    Battery(BigDecimal price) {
        this.price = price;
    }
}
