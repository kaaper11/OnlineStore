package exception;

public class UnknownProductTypeException extends RuntimeException {
  public UnknownProductTypeException() {
    super("Nieznany typ produktu.");
  }
}
