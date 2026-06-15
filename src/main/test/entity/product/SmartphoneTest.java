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

    Smartphone smartphone = new Smartphone(1L, "name", new BigDecimal(BigInteger.ONE), 22,
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
        Smartphone result = smartphone.configureSmartphone(color, battery);

        // then
        assertSame(smartphone, result);
    }

    @Test
    public void shouldAddAccessoryToList() {
        //when
        Accessory result = smartphone.addAccessory(accessory);

        //then
        assertThat(result).isNotNull();
        assertThat(smartphone.getAccessoryList()).contains(accessory);
        assertSame(accessory, result);
    }

    @Test
    public void shouldIncreaseListSizeAfterAddingAccessory() {
        //when
        Accessory result = smartphone.addAccessory(accessory);

        //then
        assertThat(smartphone.getAccessoryList()).hasSize(1);
        assertThat(result).isNotNull();
        assertThat(smartphone.getAccessoryList()).contains(accessory);
    }

    @Test
    public void shouldReturnTotalPriceCorrectly() {
        BigDecimal totalPrice = smartphone.getTotalPrice();

        assertThat(totalPrice).isNotNull();
        assertThat(totalPrice).isEqualTo(new BigDecimal(BigInteger.ONE));
    }

    @Test
    public void shouldReturnSmartphoneCopyCorrectly() {
        Product computer1 = smartphone.getProductCopy();

        assertThat(computer1).isNotNull();
        assertThat(computer1.getName()).isEqualTo(smartphone.getName());
    }
}
