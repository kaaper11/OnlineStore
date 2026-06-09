package service.impl;

import dto.order.OrderDto;
import entity.cart.Cart;
import entity.client.Client;
import entity.invoice.Invoice;
import entity.order.Order;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service implementation responsible for managing orders in the system.
 * It provides functionality for placing orders (including batch processing),
 * retrieving orders, calculating total price with discounts, generating invoices,
 * and clearing shopping carts after order completion.
 */
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final InvoiceRepository invoiceRepository;
    private final DiscountServiceImpl discountService;
    private final ConcurrentHashMap<Long, Object> clientLocks = new ConcurrentHashMap<>();


    /**
     * Places an order for a given client.
     * The method is synchronized per client to ensure thread safety.
     * It validates cart state, calculates total price with discounts,
     * creates an order and invoice, and clears the cart after processing.
     *
     * @param clientId the identifier of the client placing the order
     * @return the created OrderDto
     * @throws CartNotFoundException   if the cart does not exist
     * @throws ClientNotFoundException if the client does not exist
     * @throws CartEmptyException      if the cart contains no products
     */
    @Override
    public OrderDto placeOrder(Long clientId) {
        Object lock = clientLocks.computeIfAbsent(clientId, id -> new Object());
        synchronized (lock) {
            Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);
            Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

            if (cart.cheekProductsEmpty()) {
                throw new CartEmptyException();
            }

            BigDecimal totalPrice = discountService.calculateTotalCart(cart.getProducts());

            Order order = orderRepository.save(new Order(orderRepository.getNextId(), client,
                    new ArrayList<>(cart.getProducts()), totalPrice));

            invoiceRepository.save(new Invoice(invoiceRepository.getNextId(),
                    order.getId(), order.getClient(), order.getProducts(), order.getTotalPrice(), ZonedDateTime.now()));

            OrderDto orderDto = OrderMapper.mapOrderToDto(order);

            clearCart(cart);

            return orderDto;
        }
    }

    /**
     * Retrieves an order by its identifier.
     *
     * @param id the identifier of the order
     * @return the OrderDto representation of the order
     * @throws OrderNotFoundException if the order does not exist
     */
    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.getOrderById(id).orElseThrow(OrderNotFoundException::new);
        return OrderMapper.mapOrderToDto(order);
    }

    /**
     * Retrieves all orders belonging to a specific client.
     *
     * @param clientId the identifier of the client
     * @return a list of OrderDto objects for the client
     */
    @Override
    public List<OrderDto> getOrdersByClientId(Long clientId) {
        return orderRepository.getOneClientOrders(clientId).stream()
                .map(OrderMapper::mapOrderToDto)
                .toList();
    }

    /**
     * Places multiple orders in parallel for a list of clients.
     * Uses a fixed thread pool to process orders concurrently.
     *
     * @param clientIds list of client identifiers
     * @return list of created OrderDto objects
     */
    @Override
    public List<OrderDto> placeSomeOrders(List<Long> clientIds) {
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

    /**
     * Asynchronously places an order for the given client.
     * The operation is executed in a separate thread using CompletableFuture.
     *
     * @param clientId the identifier of the client placing the order
     * @return a CompletableFuture containing the created OrderDto
     */
    @Override
    public CompletableFuture<OrderDto> placeOrderAsync(Long clientId) {
        return CompletableFuture.supplyAsync(() -> placeOrder(clientId));
    }

    /**
     * Clears all products from the given cart after order placement.
     *
     * @param cart the cart to be cleared
     */
    private void clearCart(Cart cart) {
        cart.getProducts().clear();
    }
}
