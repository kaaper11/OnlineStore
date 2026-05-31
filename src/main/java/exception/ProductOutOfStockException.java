package exception;

import entity.product.type.Product;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(Product product) {
        super("Brak: " + product + " w magazynie.");
    }
}
