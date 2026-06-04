package exception;

public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException() {
        super("Brak uprawnień do podanej czynnośći.");
    }
}
