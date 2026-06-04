package exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("Nie możesz złożyć zamówienia z pustym koszykiem!");
    }
}
