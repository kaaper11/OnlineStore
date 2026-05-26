package mapper;

import dto.type.SmartphoneDto;
import entity.component.smartphoneConfig.Battery;
import entity.component.smartphoneConfig.SmartphoneColorType;
import entity.product.type.Smartphone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SmartphoneMapperTest {

    private final Smartphone smartphone = new Smartphone(1L, "name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    private final SmartphoneDto smartphoneDto = new SmartphoneDto("name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    @Test
    public void shouldMapDtoToSmartphone() {
        Smartphone result = SmartphoneMapper.mapDtoToSmartphone(smartphoneDto, 1L);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(smartphoneDto);
    }

    @Test
    public void shouldMapSmartphoneToDto() {
        SmartphoneDto result = SmartphoneMapper.mapSmartphoneToDto(smartphone);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(smartphone);
    }
}