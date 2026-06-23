package service.productfacade;

import dto.product.response.ProductResponseDto;

import java.util.List;

public interface ProductFacadeService {

    List<ProductResponseDto> getBaseProducts();
}
