package service.impl;

import dto.cart.CartDto;
import entity.cart.Cart;
import entity.product.type.Product;
import exception.CartNotFoundException;
import exception.ProductNotFoundException;
import exception.ProductOutOfStockException;
import lombok.AllArgsConstructor;
import mapper.CartMapper;
import repository.CartRepository;
import repository.ComputerRepository;
import repository.ElectronicsRepository;
import repository.SmartphoneRepository;
import service.CartService;

@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ComputerRepository computerRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ElectronicsRepository electronicsRepository;

    @Override
    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.getCartById(id).orElseThrow(CartNotFoundException::new);
        return CartMapper.mapCartToDto(cart);
    }

    @Override
    public CartDto getCartByClientId(Long clientId) {
        Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);
        return CartMapper.mapCartToDto(cart);
    }

    @Override
    public CartDto addProductToCart(Long clientId, Long productId) {
        Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);

        Product product = findProductById(productId);

        if (product.getQuantity() < 1) {
            throw new ProductOutOfStockException(product);
        }

        cart.addProduct(product);

        return CartMapper.mapCartToDto(cart);
    }

    private Product findProductById(Long id) {
        return computerRepository.getComputerById(id).<Product>map(p -> p)
                .or(() -> smartphoneRepository.getSmartphoneById(id))
                .or(() -> electronicsRepository.getElectronicsById(id))
                .orElseThrow(() -> new ProductNotFoundException("produktu"));
    }
}
