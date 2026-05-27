package entity;

import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Smartphone;
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
public class SmartphoneTest {

    @InjectMocks
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
    public void shouldReturnTrueWhenAllComponentsPresent() {
        //when
        smartphone.configureSmartphone(color, battery);
        boolean result = smartphone.isConfigured();

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenColorIsNull() {
        //when
        smartphone.configureSmartphone(null, battery);
        boolean result = smartphone.isConfigured();

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenBatteryIsNull() {
        //when
        smartphone.configureSmartphone(color, null);
        boolean result = smartphone.isConfigured();

        //then
        assertThat(result).isFalse();
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
}
