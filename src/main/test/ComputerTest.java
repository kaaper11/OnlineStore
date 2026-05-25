import entity.component.computerConfig.GraphicCard;
import entity.component.computerConfig.Processor;
import entity.component.computerConfig.Ram;
import entity.component.computerConfig.Rom;
import entity.product.type.Computer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class ComputerTest {

    @InjectMocks
    Computer computer = new Computer(1L, "name", new BigDecimal(BigInteger.ONE), 22);

    @Mock
    Processor processor;

    @Mock
    Ram ram;

    @Mock
    Rom rom;

    @Mock
    GraphicCard graphicCard;

    @Test
    public void shouldReturnSelfAfterConfiguration() {
        // when
        Computer result = computer.configureComputer(processor, ram, rom, graphicCard);

        // then
        assertSame(computer, result);
    }

    @Test
    public void shouldSetAllComponentsOnConfigure() {
        // when
        computer.configureComputer(processor, ram, rom, graphicCard);

        // then
        assertThat(computer.getProcessor()).isNotNull();
        assertThat(computer.getRam()).isNotNull();
        assertThat(computer.getRom()).isNotNull();
        assertThat(computer.getGraphicCard()).isNotNull();
    }

    @Test
    public void shouldReturnTrueWhenAllComponentsPresent() {
        //when
        computer.configureComputer(processor, ram, rom, graphicCard);
        boolean result = computer.isConfigured();

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenProcessorIsNull() {
        //when
        computer.configureComputer(null, ram, rom, graphicCard);
        boolean result = computer.isConfigured();

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenRamIsNull() {
        //when
        computer.configureComputer(processor, null, rom, graphicCard);
        boolean result = computer.isConfigured();

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenRomIsNull() {
        //when
        computer.configureComputer(processor, ram, null, graphicCard);
        boolean result = computer.isConfigured();

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenGraphicCardIsNull() {
        //when
        computer.configureComputer(processor, ram, rom, null);
        boolean result = computer.isConfigured();

        //then
        assertThat(result).isFalse();
    }
}
