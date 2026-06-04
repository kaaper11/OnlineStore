package dto.product.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
}
