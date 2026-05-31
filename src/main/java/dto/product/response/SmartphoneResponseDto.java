package dto.product.response;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SmartphoneResponseDto extends ProductResponseDto {
    private final SmartphoneColorType color;
    private final Battery battery;
    private final List<Accessory> accessoryList = new ArrayList<>();

    public SmartphoneResponseDto(Long id, String name, BigDecimal price, int quantity, SmartphoneColorType color,
                                 Battery battery) {
        super(id, name, price, quantity);
        this.color = color;
        this.battery = battery;
    }
}
