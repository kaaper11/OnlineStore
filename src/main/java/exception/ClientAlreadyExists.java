package exception;

public class ClientAlreadyExists extends RuntimeException {
    public ClientAlreadyExists() {
        super("Klient o podanym email już istnieje.");
    }
}
