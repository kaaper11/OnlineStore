package dto.product.request;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
public class ComputerRequestDto extends ProductRequestDto {

    public ComputerRequestDto(String name, BigDecimal price, int quantity) {
        super(name, price, quantity);
    }
}
