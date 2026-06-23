package mapper;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.product.type.Computer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ComputerMapperTest {

    private final Computer computer = new Computer(1L, "name", new BigDecimal("100"), 20);

    private final ComputerRequestDto computerRequestDto = new ComputerRequestDto("name", new BigDecimal("100"),
            20);

    @Test
    public void shouldMapDtoToComputer() {
        Computer result = ComputerMapper.mapDtoToComputer(computerRequestDto, 1L);

        assertThat(result.getName()).isEqualTo(computerRequestDto.getName());
    }

    @Test
    public void shouldMapComputerToDto() {
        ComputerResponseDto result = ComputerMapper.mapComputerToDto(computer);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(computer);
    }
}
