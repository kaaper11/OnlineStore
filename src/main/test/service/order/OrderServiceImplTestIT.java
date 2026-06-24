package service.order;

import dto.order.OrderDto;
import dto.productconfig.ComputerConfig;
import entity.cart.Cart;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import exception.CartEmptyException;
import exception.CartNotFoundException;
import exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import service.cart.CartService;
import service.cart.CartServiceImpl;
import service.computer.ComputerServiceImpl;
import service.discount.DiscountServiceImpl;
import service.electronics.ElectronicsServiceImpl;
import service.productfacade.ProductFacadeServiceImpl;
import service.smartphone.SmartphoneServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class OrderServiceImplTestIT {

    private OrderService orderService;
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        OrderRepository orderRepository = new OrderRepository();
        CartRepository cartRepository = new CartRepository();
        ClientRepository clientRepository = new ClientRepository();
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        ComputerRepository computerRepository = new ComputerRepository();
        ElectronicsRepository electronicsRepository = new ElectronicsRepository();
        SmartphoneRepository smartphoneRepository = new SmartphoneRepository();
        DiscountRepository discountRepository = new DiscountRepository();

        ComputerServiceImpl computerService = new ComputerServiceImpl(computerRepository, clientRepository);
        SmartphoneServiceImpl smartphoneService = new SmartphoneServiceImpl(smartphoneRepository, clientRepository);
        ElectronicsServiceImpl electronicsService = new ElectronicsServiceImpl(electronicsRepository, clientRepository);
        ProductFacadeServiceImpl productFacadeService = new ProductFacadeServiceImpl(List.of(
                smartphoneService, computerService, electronicsService
        ));

        DiscountServiceImpl discountService = new DiscountServiceImpl(discountRepository, clientRepository, productFacadeService);

        orderService = new OrderServiceImpl(
                orderRepository,
                cartRepository,
                clientRepository,
                invoiceRepository,
                discountService
        );
        computerRepository.save(new Computer(1L, "name", new BigDecimal("100"), 20));

        cartService = new CartServiceImpl(cartRepository, productFacadeService);


        Address address = new Address("Polska", "Wwa", "Zlota", "17-873", 10);
        Client client = new Client(1L, "Kacper", "kacper40@wp.pl", "Cos123%dd", "123456789",
                address, Role.USER);

        clientRepository.save(client);
        cartRepository.save(new Cart(1L, client.getId(), new ArrayList<>()));
    }

    @Test
    void shouldPlaceOrder() {
        cartService.addProductToCart("computer", 1L, 1L, new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8,
                Rom.GB500, GraphicCard.RTX5050));

        OrderDto orderDto = orderService.placeOrder(1L);

        assertThat(orderDto).isNotNull();
        assertThat(orderDto.client().id()).isEqualTo(1L);
    }

    @Test
    void shouldGetOrderById() {
        cartService.addProductToCart("computer",1L, 1L, new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8,
                Rom.GB500, GraphicCard.RTX5050));

        orderService.placeOrder(1L);

        OrderDto result = orderService.getOrderById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldGetOrdersByClientId() {
        cartService.addProductToCart("computer", 1L, 1L, new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8,
                Rom.GB500, GraphicCard.RTX5050));

        orderService.placeOrder(1L);

        List<OrderDto> orders = orderService.getOrdersByClientId(1L);

        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(1);
    }

    @Test
    void shouldPlaceManyOrders() {
        cartService.addProductToCart("computer", 1L, 1L, new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8,
                Rom.GB500, GraphicCard.RTX5050));

        List<OrderDto> orders = orderService.placeManyOrders(List.of(1L));

        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.getFirst().products().getFirst().getName()).isEqualTo("name");
    }

    @Test
    void shouldPlaceOrderAsync() {
        cartService.addProductToCart("computer", 1L, 1L, new ComputerConfig(Processor.INTEL_CORE_I5, Ram.GB8,
                Rom.GB500, GraphicCard.RTX5050));

        OrderDto result = orderService.placeOrderAsync(1L).join();

        assertThat(result).isNotNull();
        assertThat(result.client().id()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenCartNotFound() {
        assertThatExceptionOfType(CartNotFoundException.class)
                .isThrownBy(() -> orderService.placeOrder(999L));
    }

    @Test
    void shouldThrowWhenCartEmpty() {
        assertThatExceptionOfType(CartEmptyException.class)
                .isThrownBy(() -> orderService.placeOrder(1L));
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> orderService.getOrderById(999L));
    }
}