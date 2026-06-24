package dto.discount;

import entity.discount.DiscountType;

import java.math.BigDecimal;

public record DiscountDto(Long id, Long productId, DiscountType discountType, BigDecimal value) {
}
