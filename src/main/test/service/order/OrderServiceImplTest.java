package service.order;

import dto.order.OrderDto;
import entity.cart.Cart;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.product.type.Electronics;
import entity.product.type.Product;
import exception.CartEmptyException;
import exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CartRepository;
import repository.ClientRepository;
import repository.InvoiceRepository;
import repository.OrderRepository;
import service.discount.DiscountServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private DiscountServiceImpl discountService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final Product PRODUCT = new Electronics(1L, "product", BigDecimal.TEN, 10);
    private static final Cart CART = new Cart(1L, 10L, List.of(PRODUCT));
    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);
    private static final Client client = new Client(1L, "name", "name@test.com", "pass",
            "123456789", ADDRESS, Role.ADMIN);
    private static final Order ORDER = new Order(1L, client, CART.getProducts(), BigDecimal.TEN);

    @Test
    public void shouldPlaceOrder() {
        List<Product> products = new ArrayList<>();
        products.add(PRODUCT);
        Cart testCart = new Cart(1L, 10L, products);

        when(cartRepository.getCartByClientId(anyLong())).thenReturn(Optional.of(testCart));
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(client));
        when(orderRepository.save(any(Order.class))).thenReturn(ORDER);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(any(Invoice.class));

        OrderDto orderDto = orderService.placeOrder(1L);

        assertThat(orderDto.totalPrice()).isEqualTo(ORDER.getTotalPrice());
        assertThat(testCart.getProducts()).hasSize(0);
    }

    @Test
    public void shouldThrowWhenCartIsEmpty() {
        Cart testCart = new Cart(1L, 10L, List.of());

        when(cartRepository.getCartByClientId(anyLong())).thenReturn(Optional.of(testCart));
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(client));

        assertThrows(CartEmptyException.class, () -> orderService.placeOrder(1L));
    }

    @Test
    public void shouldGetOrderById() {
        when(orderRepository.getOrderById(anyLong())).thenReturn(Optional.of(ORDER));

        OrderDto orderDto = orderService.getOrderById(1L);

        assertThat(orderDto.totalPrice()).isEqualTo(ORDER.getTotalPrice());
    }

    @Test
    public void throwsExceptionWhenOrderNotFoundById() {
        when(orderRepository.getOrderById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    public void shouldGetOrdersByClientId() {
        when(orderRepository.getOneClientOrders(anyLong())).thenReturn(List.of(ORDER));

        List<OrderDto> ordersDto = orderService.getOrdersByClientId(1L);

        assertThat(ordersDto).hasSize(1);
        assertThat(ordersDto.getFirst().totalPrice()).isEqualTo(ORDER.getTotalPrice());
    }
}
