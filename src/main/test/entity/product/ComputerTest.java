package entity.product;

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

    private final static Computer NAME = new Computer(1L, "name", new BigDecimal(BigInteger.ONE), 22,
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
        Computer result = NAME.configureComputer(processor, ram, rom, graphicCard);

        // then
        assertSame(NAME, result);
    }

    @Test
    public void shouldSetAllComponentsOnConfigure() {
        // when
        Computer result = NAME.configureComputer(processor, ram, rom, graphicCard);

        // then
        assertThat(NAME.getProcessor()).isNotNull();
        assertThat(NAME.getRam()).isNotNull();
        assertThat(NAME.getRom()).isNotNull();
        assertThat(NAME.getGraphicCard()).isNotNull();
        assertEquals(NAME, result);
    }

    @Test
    public void shouldReturnTotalPriceCorrectly() {
        BigDecimal totalPrice = NAME.getTotalPrice();

        assertThat(totalPrice).isNotNull();
        assertThat(totalPrice).isEqualTo(new BigDecimal(BigInteger.ONE));
    }

    @Test
    public void shouldReturnComputerCopyCorrectly() {
        Product computer1 = NAME.getProductCopy();

        assertThat(computer1).isNotNull();
        assertThat(computer1.getName()).isEqualTo(NAME.getName());
    }
}
