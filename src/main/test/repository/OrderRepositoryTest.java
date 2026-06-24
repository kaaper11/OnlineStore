package repository;

import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
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

    private static final Client CLIENT = new Client(2L, "name", "email","pass", "123456789",
            new Address("Poland", "Warsaw", "Zlota", "15-820", 10), Role.ADMIN);
    private static final Product PRODUCT = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private static final Order ORDER = new Order(1L, CLIENT, List.of(PRODUCT), BigDecimal.TEN);

    @Test
    public void shouldSaveOrder() {
        Order saveOrder = orderRepository.save(ORDER);

        assertThat(saveOrder).isNotNull();
        assertThat(saveOrder).usingRecursiveComparison().isEqualTo(ORDER);
    }

    private static Stream<Arguments> getOrderArguments() {
        Client client = new Client(2L, "name", "email","pass", "123456789",
                new Address("Poland", "Warsaw", "Zlota", "15-820", 10), Role.ADMIN);

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
        orderRepository.save(ORDER);

        final var result = orderRepository.getOrderById(id);
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
        orderRepository.save(ORDER);

        List<Order> result = orderRepository.getOneClientOrders(clientId);
        assertThat(result).hasSize(expectedSize);
    }
}
