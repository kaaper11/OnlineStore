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

/**
 * Facade service responsible for unified management of all product types.
 * It delegates operations such as creation, deletion, updates, and retrieval
 * to the appropriate ProductService implementation based on runtime type or existence.
 */
@AllArgsConstructor
public class ProductFacadeService {
    private final List<ProductService<? extends ProductResponseDto>> services;

    /**
     * Creates a new product by delegating the request to the appropriate product service
     * based on the runtime type of the provided ProductRequestDto.
     *
     * @param dto      the product request data
     * @param clientId the identifier of the client performing the operation
     * @return the created ProductResponseDto
     * @throws ProductTypeNotFoundException if no matching product service is found
     */
    public ProductResponseDto create(ProductRequestDto dto, Long clientId) {
        return services.stream()
                .filter(productService -> productService.isInstance(dto))
                .findFirst()
                .orElseThrow(ProductTypeNotFoundException::new)
                .create(dto, clientId);
    }

    /**
     * Removes a product by its identifier.
     * The appropriate service is selected based on product existence.
     *
     * @param productId the identifier of the product to remove
     * @param clientId  the identifier of the client performing the operation
     * @return the removed ProductResponseDto
     * @throws ProductNotFoundException if no product is found with the given id
     */
    public ProductResponseDto removeProduct(Long productId, Long clientId) {

        return services.stream()
                .filter(productService -> productService.exist(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("produktu"))
                .remove(productId, clientId);
    }

    /**
     * Updates the price of a product identified by productId.
     * Delegates the operation to the corresponding product service.
     *
     * @param productId the identifier of the product
     * @param clientId  the identifier of the client performing the operation
     * @param price     the new price value
     * @return the updated ProductResponseDto
     * @throws ProductNotFoundException if no product exists with the given id
     */
    public ProductResponseDto updateProductPrice(Long productId, Long clientId, BigDecimal price) {
        return services.stream()
                .filter(productService -> productService.exist(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("produktu"))
                .updatePrice(productId, clientId, price);
    }

    /**
     * Updates the quantity of a product identified by productId.
     * Delegates the operation to the appropriate product service.
     *
     * @param productId the identifier of the product
     * @param clientId  the identifier of the client performing the operation
     * @param quantity  the new quantity value
     * @return the updated ProductResponseDto
     * @throws ProductNotFoundException if no product exists with the given id
     */
    public ProductResponseDto updateProductQuantity(Long productId, Long clientId, int quantity) {
        return services.stream()
                .filter(productService -> productService.exist(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("produktu"))
                .updateQuantity(productId, clientId, quantity);
    }

    /**
     * Retrieves all products from all registered product services.
     * The result is merged, sorted by product id, and returned as a single list.
     *
     * @return a sorted list of all ProductResponseDto objects in the system
     */
    public List<ProductResponseDto> getAllProducts() {
        return services.stream()
                .map(ProductService::getAll)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ProductResponseDto::getId))
                .collect(Collectors.toList());
    }
}
