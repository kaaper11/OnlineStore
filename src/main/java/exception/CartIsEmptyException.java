package exception;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException() {
        super("Nie możesz złożyć zamówienia z pustym koszykiem!");
    }
}
