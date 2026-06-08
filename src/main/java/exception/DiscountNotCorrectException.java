package exception;

public class DiscountNotCorrectException extends RuntimeException {
    public DiscountNotCorrectException() {
        super("To nie jest poprawna promocja!");
    }
}
