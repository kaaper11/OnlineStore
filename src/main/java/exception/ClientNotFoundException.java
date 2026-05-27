package exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Brak klienta w repozytorium.");
    }
}
