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

    private static final Smartphone SMARTPHONE = new Smartphone(1L, "name", new BigDecimal("100"), 20);

    private static final SmartphoneRequestDto SMARTPHONE_DTO = new SmartphoneRequestDto("name", new BigDecimal("100"),
            20);

    @Test
    public void shouldMapDtoToSmartphone() {
        Smartphone result = SmartphoneMapper.mapDtoToSmartphone(SMARTPHONE_DTO);

        assertThat(result.getName()).isEqualTo(SMARTPHONE_DTO.getName());
    }

    @Test
    public void shouldMapSmartphoneToDto() {
        SmartphoneResponseDto result = SmartphoneMapper.mapSmartphoneToDto(SMARTPHONE);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(SMARTPHONE);
    }
}