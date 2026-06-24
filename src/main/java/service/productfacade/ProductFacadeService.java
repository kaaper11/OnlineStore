package service.productfacade;

import dto.product.response.ProductResponseDto;
import entity.product.type.Product;

import java.util.List;

public interface ProductFacadeService {

    List<ProductResponseDto> getBaseProducts();

    Product getProductById(Long id, String type);
}
