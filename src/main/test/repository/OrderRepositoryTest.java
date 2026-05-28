package repository;

import entity.client.Address;
import entity.client.Client;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @InjectMocks
    private OrderRepository orderRepository;

    private final Client client = new Client(2L, "name", "email", "123456789",
            new Address("Poland", "Warsaw", "Zlota", "15-820", 10));
    private final Product product = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private final Order order = new Order(1L, client, List.of(product), BigDecimal.TEN);

    @Test
    public void shouldSaveOrder() {
        Order saveOrder = orderRepository.save(order);

        assertThat(saveOrder).isNotNull();
        assertThat(saveOrder).usingRecursiveComparison().isEqualTo(order);
    }

    private static Stream<Arguments> getOrderArguments() {
        Client client = new Client(2L, "name", "email", "123456789",
                new Address("Poland", "Warsaw", "Zlota", "15-820", 10));

        Product product = new Electronics(1L, "product", BigDecimal.TEN, 10);

        Order order = new Order(1L, client, List.of(product), BigDecimal.TEN);
        return Stream.of(
                arguments(1L, Optional.of(order)),
                arguments(2L, Optional.empty())
        );
    }

    @ParameterizedTest
    @MethodSource("getOrderArguments")
    public void shouldGetOrderById(Long id, Optional expectedOrder) {
        orderRepository.save(order);

        Optional<Order> result = orderRepository.getOrderById(id);
        assertThat(result).isEqualTo(expectedOrder);
    }


    private static Stream<Arguments> getOrderArgumentsByClientId() {
        return Stream.of(
                arguments(2L, 1),
                arguments(1L, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getOrderArgumentsByClientId")
    public void shouldGetOrderByClientId(Long clientId, int expectedSize) {
        orderRepository.save(order);

        List<Order> result = orderRepository.getOneClientOrders(clientId);
        assertThat(result).hasSize(expectedSize);
    }
}
