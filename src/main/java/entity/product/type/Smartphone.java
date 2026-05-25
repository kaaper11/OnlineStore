package entity.product.type;

import entity.product.Configurable;
import entity.product.base.Product;
import entity.component.smartphoneConfig.Accessory;
import entity.component.smartphoneConfig.Battery;
import entity.component.smartphoneConfig.SmartphoneColorType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Smartphone extends Product implements Configurable {
    private SmartphoneColorType color;
    private Battery battery;
    private List<Accessory> accessoryList = new ArrayList<>();


    public Smartphone(long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    public void configureSmartphone(SmartphoneColorType color, Battery battery) {
        this.color = color;
        this.battery = battery;
    }

    public void addAccessory(Accessory accessory) {
        accessoryList.add(accessory);
    }

    @Override
    public boolean isConfigured() {
        return color != null && battery != null;
    }
}
