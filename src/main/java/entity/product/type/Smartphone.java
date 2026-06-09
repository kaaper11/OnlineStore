package entity.product.type;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a smartphone product with configurable features such as color,
 * battery, and optional accessories.
 * A smartphone extends the base Product class and supports dynamic price
 * calculation based on selected configuration and accessories.
 */
@Getter
public class Smartphone extends Product {
    private SmartphoneColorType color;
    private Battery battery;
    private List<Accessory> accessoryList = new ArrayList<>();


    public Smartphone(Long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    public Smartphone(Long id, String name, BigDecimal price, int quantity, SmartphoneColorType color, Battery battery) {
        super(id, name, price, quantity);
        this.color = color;
        this.battery = battery;
    }

    public Smartphone configureSmartphone(SmartphoneColorType color, Battery battery) {
        this.color = color;
        this.battery = battery;

        return this;
    }

    public Accessory addAccessory(Accessory accessory) {
        accessoryList.add(accessory);
        return accessory;
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal accessoryTotalPrice = accessoryList.stream()
                .map(Accessory::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return getPrice().add(battery.getPrice()).add(accessoryTotalPrice);
    }

    @Override
    public Product getProductCopy() {
        return new Smartphone(getId(), getName(), getPrice(), getQuantity());
    }
}
