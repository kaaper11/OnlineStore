package mapper;

import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.product.type.Smartphone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SmartphoneMapperTest {

    private final Smartphone smartphone = new Smartphone(1L, "name", new BigDecimal("100"), 20);

    private final SmartphoneRequestDto smartphoneDto = new SmartphoneRequestDto("name", new BigDecimal("100"),
            20);

    @Test
    public void shouldMapDtoToSmartphone() {
        Smartphone result = SmartphoneMapper.mapDtoToSmartphone(smartphoneDto, 1L);

        assertThat(result.getName()).isEqualTo(smartphoneDto.getName());
    }

    @Test
    public void shouldMapSmartphoneToDto() {
        SmartphoneResponseDto result = SmartphoneMapper.mapSmartphoneToDto(smartphone);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(smartphone);
    }
}