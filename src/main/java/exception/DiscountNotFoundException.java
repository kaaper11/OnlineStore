package exception;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException() {
        super("Brak promocji dla tego produktu");
    }
}
