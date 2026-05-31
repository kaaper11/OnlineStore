package mapper;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ComputerMapperTest {

    private final Computer computer = new Computer(1L, "name", new BigDecimal("100"), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    private final ComputerRequestDto computerRequestDto = new ComputerRequestDto("name", new BigDecimal("100"),
            20, Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    @Test
    public void shouldMapDtoToComputer() {
        Computer result = ComputerMapper.mapDtoToComputer(computerRequestDto, 1L);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(computerRequestDto);
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
