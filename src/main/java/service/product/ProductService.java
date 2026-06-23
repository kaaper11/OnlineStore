package service.product;

import dto.product.request.ProductRequestDto;
import dto.product.response.ProductResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService<T extends ProductResponseDto> {
    T create(ProductRequestDto dto, Long clientId);

    void remove(Long id, Long clientId);

    T updatePrice(Long id, Long clientId, BigDecimal price);

    T updateQuantity(Long id, Long clientId, int quantity);

    T getById(Long id);

    List<T> getAll();

    boolean exist(Long id);
}
