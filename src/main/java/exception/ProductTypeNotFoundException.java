package exception;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException() {
        super("Brak klasy określającej typ produktu.");
    }
}
