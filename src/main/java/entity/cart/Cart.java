package entity.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Cart {
    private Long id;
    private Long clientId;
    private List<Long> productsIds;

    public Long addProduct(Long productId) {
        productsIds.add(productId);
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(clientId, cart.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clientId);
    }
}
