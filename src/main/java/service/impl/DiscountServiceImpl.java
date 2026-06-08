package service.impl;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import dto.product.response.ProductResponseDto;
import entity.client.Client;
import entity.client.Role;
import entity.discount.Discount;
import entity.discount.DiscountType;
import entity.product.type.Product;
import exception.*;
import lombok.AllArgsConstructor;
import mapper.DiscountMapper;
import repository.ClientRepository;
import repository.DiscountRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import service.DiscountService;
import validator.DiscountValidator;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ComputerRepository computerRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ElectronicsRepository electronicsRepository;
    private final ClientRepository clientRepository;

    @Override
    public DiscountDto addDiscount(DiscountRequest discountRequest, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        if (discountRepository.exists(discountRequest.productId())) {
            throw new DiscountForProductAlreadyExists();
        }

        Product product = findProductById(discountRequest.productId());
        DiscountValidator.validate(discountRequest, product.getPrice());
        Discount discount = discountRepository.save(DiscountMapper.mapDiscountRequestToDiscount(discountRequest,
                discountRepository.getNextId()));
        return DiscountMapper.mapDiscountToDto(discount);
    }

    @Override
    public BigDecimal calculateDiscount(Product product) {
        BigDecimal price = product.getTotalPrice();

        return discountRepository.getByProductId(product.getId())
                .map(discount -> applyDiscount(price, discount))
                .orElse(price);
    }

    @Override
    public DiscountDto getDiscountForProduct(Long productId) {
        Discount discount = discountRepository.getByProductId(productId).orElseThrow(DiscountNotFoundException::new);
        return DiscountMapper.mapDiscountToDto(discount);
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal productPrice, Discount discount) {
        return switch (discount.getDiscountType()) {
            case PERCENT -> {
                BigDecimal percent = BigDecimal.ONE.subtract(discount.getValue().divide(BigDecimal.valueOf(100)));
                yield productPrice.multiply(percent);
            }
            case CONSTANT -> productPrice.subtract(discount.getValue());
        };
    }

    @Override
    public BigDecimal calculateTotalCart(List<Product> products) {
        return products.stream()
                .map(this::calculateDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String formatProductWithDiscount(ProductResponseDto product) {
        return discountRepository.getByProductId(product.getId())
                .map(discount -> {
                    String discountInfo = discount.getDiscountType() == DiscountType.PERCENT
                            ? "-" + discount.getValue() + "%"
                            : "-" + discount.getValue() + "zł";
                    return "(" + discountInfo + ")";
                })
                .orElse("");
    }

    private Product findProductById(Long id) {
        return computerRepository.getComputerById(id).<Product>map(p -> p)
                .or(() -> smartphoneRepository.getSmartphoneById(id))
                .or(() -> electronicsRepository.getElectronicsById(id))
                .orElseThrow(() -> new ProductNotFoundException("produktu"));
    }
}
