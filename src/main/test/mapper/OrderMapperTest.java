package mapper;

import dto.order.OrderDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    @Test
    void shouldMapOrderToDto() {
        Client client = new Client(2L, "name", "email","pass", "123456789",
                new Address("Poland", "Warsaw", "Zlota", "15-820", 10), Role.USER);
        Product product = new Electronics(1L, "product", BigDecimal.TEN, 10);
        Order order = new Order(1L, client, List.of(product), BigDecimal.TEN);

        OrderDto result = OrderMapper.mapOrderToDto(order);

        assertThat(result).isNotNull();
        assertThat(result.client().name()).isEqualTo(order.getClient().getName());
    }
}
