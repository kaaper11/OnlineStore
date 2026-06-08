package validator;

import dto.discount.DiscountRequest;
import entity.discount.DiscountType;
import exception.DiscountNotCorrectException;

import java.math.BigDecimal;

public class DiscountValidator {

    public static void validate(DiscountRequest dto, BigDecimal productPrice) {
        percentValidate(dto);
        constantValidate(dto, productPrice);
    }

    private static void percentValidate(DiscountRequest dto) {
        if (dto.discountType() == DiscountType.PERCENT &&
                (dto.value().compareTo(new BigDecimal("100")) > 0) ||
                (dto.value().compareTo(BigDecimal.ONE) < 0)) {
            throw new DiscountNotCorrectException();
        }
    }

    private static void constantValidate(DiscountRequest dto, BigDecimal productPrice) {
        if (dto.discountType() == DiscountType.CONSTANT &&
                (dto.value().compareTo(productPrice) > 0 && dto.value().compareTo(BigDecimal.ONE) < 0) ) {
            throw new DiscountNotCorrectException();
        }
    }
}
