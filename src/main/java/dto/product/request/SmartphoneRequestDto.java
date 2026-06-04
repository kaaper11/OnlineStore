package dto.product.request;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SmartphoneRequestDto extends ProductRequestDto {

    private final SmartphoneColorType color = SmartphoneColorType.BLACK;
    private final Battery battery = Battery.MAH5500;
    private final List<Accessory> accessoryList = new ArrayList<>();

    public SmartphoneRequestDto(String name, BigDecimal price, int quantity) {
        super(name, price, quantity);
    }
}
