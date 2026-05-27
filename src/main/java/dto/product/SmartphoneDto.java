package dto.product;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SmartphoneDto extends ProductDto {
    private final SmartphoneColorType color;
    private final Battery battery;
    private final List<Accessory> accessoryList = new ArrayList<>();

    public SmartphoneDto(String name, BigDecimal price, int quantity, SmartphoneColorType color,
                         Battery battery) {
        super(name, price, quantity);
        this.color = color;
        this.battery = battery;
    }
}
