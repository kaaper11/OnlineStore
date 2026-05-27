package service;

import dto.product.ProductDto;

import java.util.List;

public interface ProductService<T extends ProductDto> {
    T create(T dto);

    T remove(Long id);

    T update(Long id, T dto);

    T getById(Long id);

    List<T> getAll();
}
