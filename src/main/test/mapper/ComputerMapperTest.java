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

    private static final Computer COMPUTER = new Computer(1L, "name", new BigDecimal("100"), 20);

    private static final ComputerRequestDto COMPUTER_REQUEST_DTO = new ComputerRequestDto("name", new BigDecimal("100"),
            20);

    @Test
    public void shouldMapDtoToComputer() {
        Computer result = ComputerMapper.mapDtoToComputer(COMPUTER_REQUEST_DTO);

        assertThat(result.getName()).isEqualTo(COMPUTER_REQUEST_DTO.getName());
    }

    @Test
    public void shouldMapComputerToDto() {
        ComputerResponseDto result = ComputerMapper.mapComputerToDto(COMPUTER);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(COMPUTER);
    }
}
