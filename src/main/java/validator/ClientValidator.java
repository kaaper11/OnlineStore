package validator;

import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import exception.ValidationException;

public class ClientValidator {

    public static void validate(ClientRequestDto dto) {
        validateName(dto.name());
        validateEmail(dto.email());
        validatePassword(dto.password());
        validatePhone(dto.phone());
        validateAddress(dto.address());
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Imię nie może być puste.");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new ValidationException("Nieprawidłowy format email.");
        }
    }

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

    private static void validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{9}")) {
            throw new ValidationException("Numer telefonu musi mieć 9 cyfr.");
        }
    }

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