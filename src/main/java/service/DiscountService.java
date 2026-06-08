package service;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import dto.product.response.ProductResponseDto;
import entity.discount.Discount;
import entity.product.type.Product;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    DiscountDto addDiscount(DiscountRequest discountRequest, Long clientId);

    BigDecimal calculateDiscount(Product product);

    DiscountDto getDiscountForProduct(Long productId);

    BigDecimal applyDiscount(BigDecimal productPrice, Discount discount);

    BigDecimal calculateTotalCart(List<Product> products);

    String formatProductWithDiscount(ProductResponseDto product);

}
