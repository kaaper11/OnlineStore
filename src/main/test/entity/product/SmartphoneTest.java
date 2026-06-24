package entity.product;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Product;
import entity.product.type.Smartphone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class SmartphoneTest {

    private final static Smartphone SMARTPHONE = new Smartphone(1L, "name", new BigDecimal(BigInteger.ONE), 22,
            SmartphoneColorType.BLACK, Battery.MAH5000);

    @Mock
    SmartphoneColorType color;

    @Mock
    Battery battery;

    @Mock
    Accessory accessory;

    @Test
    public void shouldReturnSelfAfterConfiguration() {
        // when
        Smartphone result = SMARTPHONE.configureSmartphone(color, battery);

        // then
        assertSame(SMARTPHONE, result);
    }

    @Test
    public void shouldAddAccessoryToList() {
        //when
        Accessory result = SMARTPHONE.addAccessory(accessory);

        //then
        assertThat(result).isNotNull();
        assertThat(SMARTPHONE.getAccessoryList()).contains(accessory);
        assertSame(accessory, result);
    }

    @Test
    public void shouldIncreaseListSizeAfterAddingAccessory() {
        //when
        Accessory result = SMARTPHONE.addAccessory(accessory);

        //then
        assertThat(SMARTPHONE.getAccessoryList()).hasSize(1);
        assertThat(result).isNotNull();
        assertThat(SMARTPHONE.getAccessoryList()).contains(accessory);
    }

    @Test
    public void shouldReturnTotalPriceCorrectly() {
        BigDecimal totalPrice = SMARTPHONE.getTotalPrice();

        assertThat(totalPrice).isNotNull();
        assertThat(totalPrice).isEqualTo(new BigDecimal(BigInteger.ONE));
    }

    @Test
    public void shouldReturnSmartphoneCopyCorrectly() {
        Product computer1 = SMARTPHONE.getProductCopy();

        assertThat(computer1).isNotNull();
        assertThat(computer1.getName()).isEqualTo(SMARTPHONE.getName());
    }
}
