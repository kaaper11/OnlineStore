package service.impl;

import dto.order.OrderDto;
import entity.cart.Cart;
import entity.client.Client;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.product.type.Product;
import exception.CartEmptyException;
import exception.CartNotFoundException;
import exception.ClientNotFoundException;
import exception.OrderNotFoundException;
import lombok.AllArgsConstructor;
import mapper.OrderMapper;
import repository.CartRepository;
import repository.ClientRepository;
import repository.InvoiceRepository;
import repository.OrderRepository;
import service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final InvoiceRepository invoiceRepository;
    private final ConcurrentHashMap<Long, Object> clientLocks = new ConcurrentHashMap<>();


    @Override
    public OrderDto placeOrder(Long clientId) {
        Object lock = clientLocks.computeIfAbsent(clientId, id -> new Object());
        synchronized (lock) {
            Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);
            Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

            if (cart.cheekProductsEmpty()) {
                throw new CartEmptyException();
            }

            BigDecimal totalPrice = getTotalPrice(cart);

            Order order = orderRepository.save(new Order(orderRepository.getNextId(), client,
                    new ArrayList<>(cart.getProducts()), totalPrice));

            invoiceRepository.save(new Invoice(invoiceRepository.getNextId(),
                    order.getId(), order.getClient(), order.getProducts(), order.getTotalPrice(), LocalDateTime.now()));

            OrderDto orderDto = OrderMapper.mapOrderToDto(order);

            clearCart(cart);

            return orderDto;
        }
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.getOrderById(id).orElseThrow(OrderNotFoundException::new);
        return OrderMapper.mapOrderToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByClientId(Long clientId) {
        return orderRepository.getOneClientOrders(clientId).stream()
                .map(OrderMapper::mapOrderToDto)
                .toList();
    }

    @Override
    public List<OrderDto> placeOrdersBatch(List<Long> clientIds) {
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(clientIds.size(), 4));

        List<CompletableFuture<OrderDto>> futures = clientIds.stream()
                .map(clientId -> CompletableFuture.supplyAsync(
                        () -> placeOrder(clientId), executor
                ))
                .toList();

        List<OrderDto> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        executor.shutdown();
        return results;
    }

    private BigDecimal getTotalPrice(Cart cart) {
        return cart.getProducts().stream()
                .map(Product::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void clearCart(Cart cart) {
        cart.getProducts().clear();
    }
}
