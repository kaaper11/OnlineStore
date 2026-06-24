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
    public static void runValidate(ClientRequestDto dto) {
        validate(dto.name(), ClientValidator::valueIsBlank, "Imię nie może być puste.");
        validate(dto.email(), email -> valueIsBlank(email) || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$"),
                "Nieprawidłowy format email.");
        validate(dto.password(), ClientValidator::valueIsBlank, "Hasło nie może być puste.");
        validate(dto.password(), pass -> pass.length() > 8, "Hasło musi być dłuższe niż 8 znaków");
        validate(dto.password(), pass -> !pass.matches(".*[A-Z].*"),
                "Hasło musi zawierać minimum 1 dużą literę.");
        validate(dto.password(), pass -> !pass.matches(".*[0-9].*"),
                "Hasło musi zawierać minimum 1 cyfrę.");
        validate(dto.password(), pass -> !pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"),
                "Hasło musi zawierać minimum 1 znak specjalny.");
        validatePhone(dto.phone());
        validate(dto.phone(), ClientValidator::valueIsBlank, "Numer nir może być pusty.");
        validate(dto.phone(), phone -> !phone.matches("\\d{9}"), "Numer musi składać się z 9 liczb.");

        validate(dto.address(), Objects::nonNull, "Adres nie może być pusty.");
        validate(dto.address().country(), country -> country != null && !country.isBlank(),
                "Kraj nie może być pusty.");
        validate(dto.address().city(), city -> city != null && !city.isBlank(),
                "Miasto nie może być puste.");
        validate(dto.address().street(), street -> street != null && !street.isBlank(),
                "Ulica nie może być pusty.");
        validate(dto.address().zip(), zip -> zip != null && zip.matches("^[0-9]{2}-[0-9]{3}$"),
                "Kod pocztowy musi być w formacie XX-XXX.");
        validate(dto.address().number(), number -> number > 0,
                "Numer domu musi być większy od 0.");
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

    private static <T> void validate(T value, Predicate<T> predicate, String message) {
        if (!predicate.test(value)) {
            throw new ValidationException(message);
        }
    }

    private static boolean valueIsBlank(String value) {
        return value == null || value.isBlank();
    }
}