package mapper;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.discount.Discount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting between Discount entities
 * and Discount DTO objects.
 * This class provides static methods used to transform discount data between
 * domain and transport layers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DiscountMapper {

    public static DiscountDto mapDiscountToDto(Discount discount) {
        return new DiscountDto(discount.getId(), discount.getProductId(), discount.getDiscountType(),
                discount.getValue());
    }

    public static Discount mapDiscountRequestToDiscount(DiscountRequest discountRequest, Long id) {
        return new Discount(id, discountRequest.productId(), discountRequest.discountType(), discountRequest.value());
    }
}
