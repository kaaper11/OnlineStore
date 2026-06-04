package dto.product.response;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import java.math.BigDecimal;
import java.util.List;

public class SmartphoneResponseDto extends ProductResponseDto {
    private final SmartphoneColorType color;
    private final Battery battery;
    private final List<Accessory> accessoryList;

    public SmartphoneResponseDto(Long id, String name, BigDecimal price, int quantity, SmartphoneColorType color,
                                 Battery battery, List<Accessory> accessoryList) {
        super(id, name, price, quantity);
        this.color = color;
        this.battery = battery;
        this.accessoryList = accessoryList;
    }

    @Override
    public String toString() {
        return color != null && battery != null ? "Smartphone: (" + "nazwa: " + getName() + ", cena: " + getPrice()
                + ", dostępność: " + getQuantity() + ", kolor: " + color + ", pojemność baterii: " + battery + ", akcesoria: "
                + accessoryList
                : "Smartphone: (" + "nazwa:" + getName() + ", cena:" + getPrice() + ", dostępność" + getQuantity() + ")";
    }
}
