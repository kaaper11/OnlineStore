package entity.cart;

import entity.product.type.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Represents a shopping cart associated with a specific client.
 * The cart contains a list of products selected by the client and
 * provides basic operations for managing its contents.
 * <p>
 * Equality of carts is based on the client identifier, meaning that
 * two carts are considered equal if they belong to the same client.
 */

@AllArgsConstructor
@Getter
public class Cart {
    private Long id;
    private Long clientId;
    private List<Product> products;

    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    public boolean cheekProductsEmpty() {
        return products.isEmpty();
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
