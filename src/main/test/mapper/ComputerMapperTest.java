package mapper;

import dto.product.ComputerDto;
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

    private final ComputerDto computerDto = new ComputerDto("name", new BigDecimal("100"),
            20, Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    @Test
    public void shouldMapDtoToComputer() {
        Computer result = ComputerMapper.mapDtoToComputer(computerDto, 1L);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(computerDto);
    }

    @Test
    public void shouldMapComputerToDto() {
        ComputerDto result = ComputerMapper.mapComputerToDto(computer);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(computer);
    }
}
