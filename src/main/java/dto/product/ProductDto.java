package dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ProductDto {
    private final String name;
    private final BigDecimal price;
    private final int quantity;
}
