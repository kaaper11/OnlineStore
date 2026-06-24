package dto.productconfig;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Product;
import entity.product.type.Smartphone;

import java.util.List;

public record SmartphoneConfig(SmartphoneColorType colorType, Battery battery, List<Accessory> accessoryList)
        implements ProductConfig {

    @Override
    public void configure(Product product) {
        Smartphone smartphone = (Smartphone) product;
        smartphone.configureSmartphone(colorType, battery);
        accessoryList.forEach(smartphone::addAccessory);
    }
}
