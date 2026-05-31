package dto.product.response;

import java.math.BigDecimal;

public class ElectronicsResponseDto extends ProductResponseDto {

    public ElectronicsResponseDto(Long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }
}
