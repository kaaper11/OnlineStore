package entity;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class ComputerTest {

    Computer computer = new Computer(1L, "name", new BigDecimal(BigInteger.ONE), 22,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

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
        Computer result = computer.configureComputer(processor, ram, rom, graphicCard);

        // then
        assertThat(computer.getProcessor()).isNotNull();
        assertThat(computer.getRam()).isNotNull();
        assertThat(computer.getRom()).isNotNull();
        assertThat(computer.getGraphicCard()).isNotNull();
        assertEquals(computer, result);
    }

    @Test
    public void shouldReturnTotalPriceCorrectly() {
        BigDecimal totalPrice = computer.getTotalPrice();

        assertThat(totalPrice).isNotNull();
        assertThat(totalPrice).isEqualTo(new BigDecimal(BigInteger.ONE));
    }

    @Test
    public void shouldReturnComputerCopyCorrectly() {
        Product computer1 = computer.getProductCopy();

        assertThat(computer1).isNotNull();
        assertThat(computer1.getName()).isEqualTo(computer.getName());
    }
}
