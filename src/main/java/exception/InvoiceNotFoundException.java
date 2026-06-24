package exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException() {
        super("Nie znaleziono faktury w repozytorium.");
    }
}
