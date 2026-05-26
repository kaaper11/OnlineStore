package entity.product.type;

import entity.component.smartphoneConfig.Accessory;
import entity.component.smartphoneConfig.Battery;
import entity.component.smartphoneConfig.SmartphoneColorType;
import entity.product.Configurable;
import entity.product.base.Product;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Smartphone extends Product implements Configurable {
    private SmartphoneColorType color;
    private Battery battery;
    private List<Accessory> accessoryList = new ArrayList<>();


    public Smartphone(Long id, String name, BigDecimal price, int quantity, SmartphoneColorType color,
                      Battery battery) {
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
    public boolean isConfigured() {
        return color != null && battery != null;
    }
}
