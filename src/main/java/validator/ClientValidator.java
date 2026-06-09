package validator;

import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import exception.ValidationException;

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
        validateAddress(dto.address());
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
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
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

    /**
     * Validates the client's address data.
     * Ensures that all address fields are present and correctly formatted.
     *
     * @param address the address DTO to validate
     * @throws ValidationException if any address field is invalid or missing
     */
    private static void validateAddress(AddressDto address) {
        if (address == null) {
            throw new ValidationException("Adres nie może być pusty.");
        }
        if (address.country() == null || address.country().isBlank()) {
            throw new ValidationException("Kraj nie może być pusty.");
        }
        if (address.city() == null || address.city().isBlank()) {
            throw new ValidationException("Miasto nie może być puste.");
        }
        if (address.street() == null || address.street().isBlank()) {
            throw new ValidationException("Ulica nie może być pusta.");
        }
        if (address.zip() == null || !address.zip().matches("\\d{2}-\\d{3}")) {
            throw new ValidationException("Kod pocztowy musi być w formacie XX-XXX.");
        }
        if (address.number() <= 0) {
            throw new ValidationException("Numer domu musi być większy od 0.");
        }
    }
}