package validator;

import dto.discount.DiscountRequest;
import entity.discount.DiscountType;
import exception.DiscountNotCorrectException;

import java.math.BigDecimal;

/**
 * Validator responsible for validating discount rules before saving or applying a discount.
 * It ensures that discount values are within acceptable bounds depending on discount type
 * (PERCENT or CONSTANT) and that they do not exceed logical constraints such as product price.
 */
public class DiscountValidator {

    /**
     * Validates a discount request against business rules and product price.
     *
     * @param dto          the discount request to validate
     * @param productPrice the price of the product to which the discount will be applied
     * @throws DiscountNotCorrectException if discount rules are violated
     */
    public static void validate(DiscountRequest dto, BigDecimal productPrice) {
        percentValidate(dto);
        constantValidate(dto, productPrice);
    }

    /**
     * Validates percentage-based discount values.
     * Ensures that percentage discount is within valid range (1% to 100%).
     *
     * @param dto the discount request containing discount type and value
     * @throws DiscountNotCorrectException if percent discount is outside allowed range
     */
    private static void percentValidate(DiscountRequest dto) {
        if (dto.discountType() == DiscountType.PERCENT &&
                (dto.value().compareTo(new BigDecimal("100")) > 0) ||
                (dto.value().compareTo(BigDecimal.ONE) < 0)) {
            throw new DiscountNotCorrectException();
        }
    }

    /**
     * Validates constant (fixed amount) discount values.
     * Ensures that the discount does not exceed the product price
     * and is not less than 1 unit.
     *
     * @param dto          the discount request containing discount type and value
     * @param productPrice the price of the product being discounted
     * @throws DiscountNotCorrectException if constant discount is invalid
     */
    private static void constantValidate(DiscountRequest dto, BigDecimal productPrice) {
        if (dto.discountType() == DiscountType.CONSTANT &&
                (dto.value().compareTo(productPrice) > 0 && dto.value().compareTo(BigDecimal.ONE) < 0)) {
            throw new DiscountNotCorrectException();
        }
    }
}
