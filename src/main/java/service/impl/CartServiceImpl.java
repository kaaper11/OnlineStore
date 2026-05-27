package service.impl;

import dto.cart.CartRequestDto;
import dto.cart.CartResponseDto;
import entity.cart.Cart;
import entity.product.type.Product;
import exception.CartNotFoundException;
import exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import mapper.CartMapper;
import repository.CartRepository;
import repository.ComputerRepository;
import repository.ElectronicsRepository;
import repository.SmartphoneRepository;
import service.CartService;

import java.util.List;

@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ComputerRepository computerRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ElectronicsRepository electronicsRepository;

    @Override
    public CartResponseDto createCart(CartRequestDto cartRequestDto) {
        Cart cart = cartRepository.save(CartMapper.mapDtoToCart(cartRequestDto, cartRepository.getNextId()));
        List<Product> products = findProductsByIds(cart.getProductsIds());
        return CartMapper.mapCartToCartResponseDto(cart, products);
    }

    @Override
    public CartResponseDto removeCart(Long id) {
        Cart cart = cartRepository.delete(id)
                .orElseThrow(CartNotFoundException::new);
        List<Product> products = findProductsByIds(cart.getProductsIds());
        return CartMapper.mapCartToCartResponseDto(cart, products);
    }

    @Override
    public CartResponseDto updateCart(Long id, CartRequestDto cartRequestDto) {
        Cart cart = cartRepository.update(id, CartMapper.mapDtoToCart(cartRequestDto, id))
                .orElseThrow(CartNotFoundException::new);
        List<Product> products = findProductsByIds(cart.getProductsIds());
        return CartMapper.mapCartToCartResponseDto(cart, products);
    }

    @Override
    public CartResponseDto getCartById(Long id) {
        Cart cart = cartRepository.getCartById(id)
                .orElseThrow(CartNotFoundException::new);
        List<Product> products = findProductsByIds(cart.getProductsIds());
        return CartMapper.mapCartToCartResponseDto(cart, products);
    }

    @Override
    public CartResponseDto getCartByClientId(Long clientId) {
        Cart cart = cartRepository.getCartByClientId(clientId)
                .orElseThrow(CartNotFoundException::new);
        List<Product> products = findProductsByIds(cart.getProductsIds());
        return CartMapper.mapCartToCartResponseDto(cart, products);
    }

    private List<Product> findProductsByIds(List<Long> ids) {
        return ids.stream()
                .map(id -> computerRepository.getComputerById(id)
                        .<Product>map(computer -> computer)
                        .or(() -> electronicsRepository.getElectronicsById(id))
                        .or(() -> smartphoneRepository.getSmartphoneById(id))
                        .orElseThrow(() -> new ProductNotFoundException("produktu"))
                )
                .toList();
    }
}
