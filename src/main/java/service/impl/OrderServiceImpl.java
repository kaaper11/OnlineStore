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

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public OrderDto placeOrder(Long clientId) {
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

    private BigDecimal getTotalPrice(Cart cart) {
        return cart.getProducts().stream()
                .map(Product::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void clearCart(Cart cart) {
        cart.getProducts().clear();
    }
}
