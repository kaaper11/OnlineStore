package dto.productconfig;

import entity.product.type.Product;

public record ElectonicsConfig() implements ProductConfig {
    @Override
    public void configure(Product product) {
    }
}
