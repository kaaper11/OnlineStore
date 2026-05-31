package service;

import dto.product.response.ProductResponseDto;

import java.util.List;

public interface ProductService<T extends ProductResponseDto, R> {
    T create(R dto);

    T remove(Long id);

    T update(Long id, R dto);

    T getById(Long id);

    List<T> getAll();
}
