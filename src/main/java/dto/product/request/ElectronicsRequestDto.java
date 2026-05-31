package dto.product.request;

import java.math.BigDecimal;

public class ElectronicsRequestDto extends ProductRequestDto {

    public ElectronicsRequestDto(String name, BigDecimal price, int quantity) {
        super(name, price, quantity);
    }
}
