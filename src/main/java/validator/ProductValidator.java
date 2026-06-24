package validator;

import dto.product.request.ProductRequestDto;
import exception.ValidationException;

import java.math.BigDecimal;

/**
 * Validator responsible for validating product data before creation or update.
 * It ensures that product name, price, and quantity meet required business rules
 * such as non-null values, positive numbers, and minimum length constraints.
 */
public class ProductValidator {

    /**
     * Validates a complete ProductRequestDto by checking its name, price, and quantity.
     *
     * @param dto the product request to validate
     * @throws ValidationException if any product field is invalid
     */
    public static void validate(ProductRequestDto dto) {
        validateName(dto.getName());
        validatePrice(dto.getPrice());
        validateQuantity(dto.getQuantity());
    }

    /**
     * Validates the product name.
     * Ensures that the name is not null, not blank, and has at least 2 characters.
     *
     * @param name the product name to validate
     * @throws ValidationException if the name is invalid
     */
    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nazwa produktu nie może być pusta.");
        }
        if (name.length() < 2) {
            throw new ValidationException("Nazwa produktu musi mieć minimum 2 znaki.");
        }
    }

    /**
     * Validates the product price.
     * Ensures that price is not null and greater than zero.
     *
     * @param price the price to validate
     * @throws ValidationException if price is null or not positive
     */
    public static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new ValidationException("Cena nie może być pusta.");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Cena musi być większa od 0.");
        }
    }

    /**
     * Validates the product quantity.
     * Ensures that quantity is greater than zero.
     *
     * @param quantity the quantity to validate
     * @throws ValidationException if quantity is not greater than zero
     */
    public static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new ValidationException("Ilość musi być większa od 0.");
        }
    }
}