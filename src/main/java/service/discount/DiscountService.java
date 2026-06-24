package service.discount;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.discount.Discount;
import entity.product.type.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DiscountService {
    DiscountDto addDiscount(String type, DiscountRequest discountRequest, Long clientId);

    BigDecimal calculateDiscount(Product product);

    Optional<DiscountDto> getDiscountForProduct(Long productId);

    BigDecimal applyDiscount(BigDecimal productPrice, Discount discount);

    BigDecimal calculateTotalCart(List<Product> products);

}
