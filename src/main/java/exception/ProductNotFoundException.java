package exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productType) {
        super("Brak " + productType + " o podanym id w sklepie.");
    }
}
