package exception;

public class DiscountForProductAlreadyExists extends RuntimeException {
    public DiscountForProductAlreadyExists() {
        super("Promocja ne ten produkt już istnieje!");
    }
}
