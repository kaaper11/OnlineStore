package service.productfacade;

import dto.product.response.ProductResponseDto;
import lombok.AllArgsConstructor;
import service.product.ProductService;

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
public class ProductFacadeServiceImpl implements ProductFacadeService {
    private final List<ProductService<? extends ProductResponseDto>> services;

    /**
     * Retrieves all products from all registered product services.
     * The result is merged, sorted by product id, and returned as a single list.
     *
     * @return a sorted list of all ProductResponseDto objects in the system
     */
    @Override
    public List<ProductResponseDto> getBaseProducts() {
        return services.stream()
                .map(ProductService::getAll)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ProductResponseDto::getId))
                .collect(Collectors.toList());
    }
}
