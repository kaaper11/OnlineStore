package validator;

import dto.product.request.ProductRequestDto;
import exception.ValidationException;

import java.math.BigDecimal;

public class ProductValidator {

    public static void validate(ProductRequestDto dto) {
        validateName(dto.getName());
        validatePrice(dto.getPrice());
        validateQuantity(dto.getQuantity());
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nazwa produktu nie może być pusta.");
        }
        if (name.length() < 2) {
            throw new ValidationException("Nazwa produktu musi mieć minimum 2 znaki.");
        }
    }

    public static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new ValidationException("Cena nie może być pusta.");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Cena musi być większa od 0.");
        }
    }

    public static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new ValidationException("Ilość musi być większa od 0.");
        }
    }
}