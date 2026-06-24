package mapper;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.discount.Discount;
import entity.discount.DiscountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiscountMapperTest {
    @Test
    void shouldMapDiscountToDtoCorrectly() {
        Discount discount = new Discount(1L, 100L, DiscountType.PERCENT, new BigDecimal("20"));

        DiscountDto dto = DiscountMapper.mapDiscountToDto(discount);

        assertThat(dto).isNotNull();
        assertEquals(1L, dto.id());
        assertEquals(100L, dto.productId());
        assertEquals(DiscountType.PERCENT, dto.discountType());
        assertEquals(new BigDecimal("20"), dto.value());
    }

    @Test
    void shouldMapDiscountRequestToDiscountCorrectly() {
        DiscountRequest request = new DiscountRequest(200L, DiscountType.PERCENT, new BigDecimal("15.5"));

        Discount discount = DiscountMapper.mapDiscountRequestToDiscount(request);

        assertThat(discount).isNotNull();
        assertEquals(5L, discount.getId());
        assertEquals(200L, discount.getProductId());
        assertEquals(DiscountType.PERCENT, discount.getDiscountType());
        assertEquals(new BigDecimal("15.5"), discount.getValue());
    }
}
