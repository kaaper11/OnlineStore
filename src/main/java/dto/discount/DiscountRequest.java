package dto.discount;

import entity.discount.DiscountType;

import java.math.BigDecimal;

public record DiscountRequest(Long productId, DiscountType discountType, BigDecimal value) {
}
