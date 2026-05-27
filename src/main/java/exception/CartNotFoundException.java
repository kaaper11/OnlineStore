package exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Brak koszyka!");
    }
}
