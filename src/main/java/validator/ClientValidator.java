package validator;

import dto.client.ClientRequestDto;
import exception.ValidationException;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Validator responsible for validating client data before persistence.
 * It ensures that all required fields such as name, email, password,
 * phone number, and address meet the defined business rules and formats.
 */
public class ClientValidator {

    /**
     * Validates the entire ClientRequestDto by delegating to specific field validators.
     *
     * @param dto the client request data to validate
     * @throws ValidationException if any validation rule is violated
     */
    public static void validate(ClientRequestDto dto) {
        validateName(dto.name());
        validateEmail(dto.email());
        validatePassword(dto.password());
        validatePhone(dto.phone());

        validateAddress(dto.address(), Objects::nonNull, "Adres nie może być pusty.");
        validateAddress(dto.address().country(), country -> country != null && !country.isBlank(),
                "Kraj nie może być pusty.");
        validateAddress(dto.address().city(), city -> city != null && !city.isBlank(),
                "Miasto nie może być puste.");
        validateAddress(dto.address().street(), street -> street != null && !street.isBlank(),
                "Ulica nie może być pusty.");
        validateAddress(dto.address().zip(), zip -> zip != null && zip.matches("^[0-9]{2}-[0-9]{3}$"),
                "Kod pocztowy musi być w formacie XX-XXX.");
        validateAddress(dto.address().number(), number -> number > 0,
                "Numer domu musi być większy od 0.");
    }

    /**
     * Validates the client's name.
     *
     * @param name the name to validate
     * @throws ValidationException if the name is null or blank
     */
    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Imię nie może być puste.");
        }
    }

    /**
     * Validates the client's email format using a regular expression.
     *
     * @param email the email to validate
     * @throws ValidationException if the email is null or has invalid format
     */
    private static void validateEmail(String email) {
        if (email == null || email.isBlank() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new ValidationException("Nieprawidłowy format email.");
        }
    }

    /**
     * Validates the client's password strength requirements.
     * Password must be at least 8 characters long and contain:
     * - at least one uppercase letter
     * - at least one digit
     * - at least one special character
     *
     * @param password the password to validate
     * @throws ValidationException if password does not meet security rules
     */
    private static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Hasło musi mieć minimum 8 znaków.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Hasło musi zawierać minimum 1 dużą literę.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new ValidationException("Hasło musi zawierać minimum 1 cyfrę.");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            throw new ValidationException("Hasło musi zawierać minimum 1 znak specjalny.");
        }
    }

    /**
     * Validates the client's phone number.
     *
     * @param phone the phone number to validate
     * @throws ValidationException if phone is null or does not contain exactly 9 digits
     */
    private static void validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{9}")) {
            throw new ValidationException("Numer telefonu musi mieć 9 cyfr.");
        }
    }

    private static <T> void validateAddress(T value, Predicate<T> predicate, String message) {
        if (!predicate.test(value)) {
            throw new ValidationException(message);
        }
    }
}