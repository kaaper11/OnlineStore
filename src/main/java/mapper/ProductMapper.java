package mapper;

import dto.product.response.ProductResponseDto;
import entity.product.type.Computer;
import entity.product.type.Electronics;
import entity.product.type.Product;
import entity.product.type.Smartphone;
import exception.UnknownProductTypeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting Product entities
 * into their corresponding ProductResponseDto representations.
 * This class uses type-based dispatching to select the correct mapper
 * depending on the конкрет product implementation.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductMapper {

    public static ProductResponseDto mapProductToDto(Product product) {
        return switch (product) {
            case Computer computer -> ComputerMapper.mapComputerToDto(computer);
            case Smartphone smartphone -> SmartphoneMapper.mapSmartphoneToDto(smartphone);
            case Electronics electronics -> ElectronicsMapper.mapElectronicsToDto(electronics);
            default -> throw new UnknownProductTypeException();
        };
    }
}
