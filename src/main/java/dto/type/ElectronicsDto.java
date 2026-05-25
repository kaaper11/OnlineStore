package dto.type;

import dto.base.ProductDto;

import java.math.BigDecimal;

public class ElectronicsDto extends ProductDto {

    public ElectronicsDto(String name, BigDecimal price, int quantity) {
        super(name, price, quantity);
    }
}
