package mapper;

import dto.product.ProductDto;
import entity.product.type.Computer;
import entity.product.type.Electronics;
import entity.product.type.Product;
import entity.product.type.Smartphone;
import exception.UnknownProductTypeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductMapper {

    public static ProductDto mapProductToDto(Product product) {
        return switch (product) {
            case Computer computer -> ComputerMapper.mapComputerToDto(computer);
            case Smartphone smartphone -> SmartphoneMapper.mapSmartphoneToDto(smartphone);
            case Electronics electronics -> ElectronicsMapper.mapElectronicsToDto(electronics);
            default -> throw new UnknownProductTypeException();
        };
    }
}
