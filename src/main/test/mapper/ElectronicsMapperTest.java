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

    private static final Electronics ELECTRONICS = new Electronics(1L, "name", new BigDecimal("100"), 20);

    private static final ElectronicsRequestDto ELECTRONICS_REQUEST_DTO = new ElectronicsRequestDto("name", new BigDecimal("100"), 20);

    @Test
    public void shouldMapDtoToElectronics() {
        Electronics result = ElectronicsMapper.mapDtoToElectronics(ELECTRONICS_REQUEST_DTO);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ELECTRONICS_REQUEST_DTO);
    }

    @Test
    public void shouldMapElectronicsToDto() {
        ElectronicsResponseDto result = ElectronicsMapper.mapElectronicsToDto(ELECTRONICS);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ELECTRONICS);
    }
}