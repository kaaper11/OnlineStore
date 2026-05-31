package entity.product.type;

import exception.ProductOutOfStockException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public int buyProduct() {
        if (quantity == 0) {
            throw new ProductOutOfStockException(this);
        }
        quantity--;
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public abstract BigDecimal getTotalPrice();
}
