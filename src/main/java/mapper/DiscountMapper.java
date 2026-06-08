package mapper;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.discount.Discount;

public class DiscountMapper {

    public static DiscountDto mapDiscountToDto(Discount discount) {
        return new DiscountDto(discount.getId(), discount.getProductId(), discount.getDiscountType(),
                discount.getValue());
    }

    public static Discount mapDiscountRequestToDiscount(DiscountRequest discountRequest, Long id) {
        return new Discount(id, discountRequest.productId(), discountRequest.discountType(), discountRequest.value());
    }
}
