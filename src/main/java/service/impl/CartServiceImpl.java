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

/**
 * Service implementation responsible for managing shopping cart operations.
 * It provides functionality for retrieving carts and adding products to a cart,
 * including product configuration and stock validation.
 */
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ComputerRepository computerRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ElectronicsRepository electronicsRepository;

    /**
     * Retrieves a shopping cart by its identifier.
     *
     * @param id the identifier of the cart
     * @return a CartDto representing the found cart
     * @throws CartNotFoundException if no cart with the given id exists
     */
    @Override
    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.getCartById(id).orElseThrow(CartNotFoundException::new);
        return CartMapper.mapCartToDto(cart);
    }

    /**
     * Retrieves a shopping cart by client identifier.
     *
     * @param clientId the identifier of the client
     * @return a CartDto representing the client's cart
     * @throws CartNotFoundException if no cart for the given client exists
     */
    @Override
    public CartDto getCartByClientId(Long clientId) {
        Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);
        return CartMapper.mapCartToDto(cart);
    }

    /**
     * Adds a product to the client's cart with optional configuration.
     * The method validates product availability, applies configuration,
     * decreases product stock, and adds a configured copy of the product to the cart.
     *
     * @param clientId      the identifier of the client
     * @param productId     the identifier of the product to add
     * @param productConfig configuration applied to the product before adding
     * @return updated CartDto after adding the product
     * @throws CartNotFoundException      if the cart does not exist
     * @throws ProductNotFoundException   if the product cannot be found
     * @throws ProductOutOfStockException if the product has no available quantity
     */
    @Override
    public CartDto addProductToCart(Long clientId, Long productId, ProductConfig productConfig) {
        Cart cart = cartRepository.getCartByClientId(clientId).orElseThrow(CartNotFoundException::new);

        Product originalProduct = findProductById(productId);

        if (originalProduct.getQuantity() < 1) {
            throw new ProductOutOfStockException(originalProduct);
        }

        Product product = originalProduct.getProductCopy();

        productConfig.configure(product);

        originalProduct.buyProduct();

        cart.addProduct(product);

        return CartMapper.mapCartToDto(cart);
    }

    /**
     * Searches for a product by its identifier across all product repositories.
     *
     * @param id the identifier of the product
     * @return the found Product instance
     * @throws ProductNotFoundException if no product exists with the given id
     */
    private Product findProductById(Long id) {
        return computerRepository.getComputerById(id).<Product>map(product -> product)
                .or(() -> smartphoneRepository.getSmartphoneById(id))
                .or(() -> electronicsRepository.getElectronicsById(id))
                .orElseThrow(() -> new ProductNotFoundException("produktu"));
    }
}
