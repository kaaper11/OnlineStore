package service;

import dto.product.request.ProductRequestDto;
import dto.product.response.ProductResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductFacadeService {

    ProductResponseDto createProduct(ProductRequestDto dto, Long clientId);

    ProductResponseDto removeProduct(Long productId, Long clientId);

    ProductResponseDto updateProductPrice(Long productId, Long clientId, BigDecimal price);

    ProductResponseDto updateProductQuantity(Long productId, Long clientId, int quantity);

    List<ProductResponseDto> getAllProducts();
}
