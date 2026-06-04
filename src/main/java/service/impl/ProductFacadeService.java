package service.impl;

import dto.product.request.ProductRequestDto;
import dto.product.response.ProductResponseDto;
import exception.ProductNotFoundException;
import exception.ProductTypeNotFoundException;
import lombok.AllArgsConstructor;
import service.ProductService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductFacadeService {

    private final List<ProductService<? extends ProductResponseDto>> services;

    public ProductResponseDto create(ProductRequestDto dto, Long clientId) {
        return services.stream()
                .filter(productService -> productService.isInstance(dto))
                .findFirst()
                .orElseThrow(ProductTypeNotFoundException::new)
                .create(dto, clientId);
    }

    public ProductResponseDto removeProduct(Long productId, Long clientId) {

        return services.stream()
                .filter(productService -> productService.exist(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("produktu"))
                .remove(productId, clientId);
    }

    public ProductResponseDto updateProductPrice(Long productId, Long clientId, BigDecimal price) {
        return services.stream()
                .filter(productService -> productService.exist(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("produktu"))
                .updatePrice(productId, clientId, price);
    }

    public ProductResponseDto updateProductQuantity(Long productId, Long clientId, int quantity) {
        return services.stream()
                .filter(productService -> productService.exist(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("produktu"))
                .updateQuantity(productId, clientId, quantity);
    }

    public List<ProductResponseDto> getAllProducts() {
        return services.stream()
                .map(ProductService::getAll)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ProductResponseDto::getId))
                .collect(Collectors.toList());
    }
}
