package mapper;

import dto.product.request.ElectronicsRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.product.type.Electronics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ElectronicsMapperTest {

    private final Electronics electronics = new Electronics(1L, "name", new BigDecimal("100"), 20);

    private final ElectronicsRequestDto electronicsRequestDto = new ElectronicsRequestDto("name", new BigDecimal("100"), 20);

    @Test
    public void shouldMapDtoToElectronics() {
        Electronics result = ElectronicsMapper.mapDtoToElectronics(electronicsRequestDto, 1L);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(electronicsRequestDto);
    }

    @Test
    public void shouldMapElectronicsToDto() {
        ElectronicsResponseDto result = ElectronicsMapper.mapElectronicsToDto(electronics);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(electronics);
    }
}