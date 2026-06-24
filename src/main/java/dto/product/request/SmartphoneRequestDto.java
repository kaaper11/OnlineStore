package dto.product.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SmartphoneRequestDto extends ProductRequestDto {

    public SmartphoneRequestDto(String name, BigDecimal price, int quantity) {
        super(name, price, quantity);
    }
}
