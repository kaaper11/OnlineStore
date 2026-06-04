package exception;

public class IncorrectPasswordException extends RuntimeException {
  public IncorrectPasswordException() {
    super("Nieprawidłowe hasło.");
  }
}
