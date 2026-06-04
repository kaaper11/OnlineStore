package mapper;

import dto.product.response.ComputerResponseDto;
import dto.product.response.ElectronicsResponseDto;
import dto.product.response.ProductResponseDto;
import dto.product.response.SmartphoneResponseDto;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Computer;
import entity.product.type.Electronics;
import entity.product.type.Product;
import entity.product.type.Smartphone;
import exception.UnknownProductTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    private final Computer computer = new Computer(2L, "Mouse", BigDecimal.valueOf(200), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    private final Smartphone smartphone = new Smartphone(2L, "iPhone", BigDecimal.valueOf(4500), 48,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    private final Electronics electronics = new Electronics(3L, "TV", BigDecimal.valueOf(3000), 24);

    @Test
    public void shouldMapComputerToDto() {
        ProductResponseDto result = ProductMapper.mapProductToDto(computer);

        ComputerResponseDto expected = ComputerMapper.mapComputerToDto(computer);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void shouldMapSmartphoneToDto() {
        ProductResponseDto result = ProductMapper.mapProductToDto(smartphone);

        SmartphoneResponseDto expected = SmartphoneMapper.mapSmartphoneToDto(smartphone);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void shouldMapElectronicsToDto() {
        ProductResponseDto result = ProductMapper.mapProductToDto(electronics);

        ElectronicsResponseDto expected = ElectronicsMapper.mapElectronicsToDto(electronics);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void shouldThrowExceptionForUnknownProductType() {
        Product unknownProduct = new Product(99L, "Unknown", BigDecimal.TEN, 28) {
            @Override
            public BigDecimal getTotalPrice() {
                return null;
            }

            @Override
            public Product getProductCopy() {
                return null;
            }
        };

        assertThrows(
                UnknownProductTypeException.class,
                () -> ProductMapper.mapProductToDto(unknownProduct)
        );
    }
}