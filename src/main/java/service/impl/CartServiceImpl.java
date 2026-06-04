package service.impl;

import dto.cart.CartDto;
import dto.productconfig.ProductConfig;
import entity.cart.Cart;
import entity.product.type.Product;
import exception.CartNotFoundException;
import exception.ProductNotFoundException;
import exception.ProductOutOfStockException;
import lombok.AllArgsConstructor;
import mapper.CartMapper;
import repository.CartRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
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
    public CartDto addProductToCart(Long clientId, Long productId, ProductConfig productConfig) {
        Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);

        Product originalProduct = findProductById(productId);

        if (originalProduct.getQuantity() < 1) {
            throw new ProductOutOfStockException(originalProduct);
        }

        originalProduct.buyProduct();

        Product product = originalProduct.getProductCopy();

        productConfig.configure(product);

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
