package exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("Nie znaleziono zamówienia w repozytorium.");
    }
}
