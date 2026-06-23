package service.discount;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.client.Client;
import entity.client.Role;
import entity.discount.Discount;
import entity.product.type.Product;
import exception.*;
import lombok.AllArgsConstructor;
import mapper.DiscountMapper;
import repository.ClientRepository;
import repository.DiscountRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import validator.DiscountValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation responsible for managing discounts in the system.
 * It provides operations for creating discounts, applying them to products,
 * calculating discounted prices, and formatting discount information,
 * while enforcing ADMIN-only access control.
 */
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ComputerRepository computerRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ElectronicsRepository electronicsRepository;
    private final ClientRepository clientRepository;

    /**
     * Adds a discount for a specific product.
     * The method validates admin permissions, checks for existing discounts,
     * validates discount rules, and persists the new discount.
     *
     * @param discountRequest the discount data to be created
     * @param clientId        the identifier of the client performing the operation
     * @return the created DiscountDto
     * @throws ClientNotFoundException         if the client does not exist
     * @throws NoPermissionsException          if the client is not an ADMIN
     * @throws DiscountForProductAlreadyExists if a discount already exists for the product
     * @throws ProductNotFoundException        if the product does not exist
     */
    @Override
    public DiscountDto addDiscount(String type, DiscountRequest discountRequest, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        if (discountRepository.exists(discountRequest.productId())) {
            throw new DiscountForProductAlreadyExists();
        }

        Product product = switch (type) {
            case "computer" -> computerRepository.getComputerById(discountRequest.productId())
                    .orElseThrow(ComputerNotFoundException::new);
            case "electronics" -> electronicsRepository.getElectronicsById(discountRequest.productId())
                    .orElseThrow(ElectronicsNotFoundException::new);
            case "smartphone" -> smartphoneRepository.getSmartphoneById(discountRequest.productId())
                    .orElseThrow(SmartphoneNotFoundException::new);
            default -> throw new ProductTypeNotFoundException();
        };

        DiscountValidator.validate(discountRequest, product.getPrice());
        Discount discount = discountRepository.save(DiscountMapper.mapDiscountRequestToDiscount(discountRequest,
                discountRepository.getNextId()));
        return DiscountMapper.mapDiscountToDto(discount);
    }

    /**
     * Calculates the discounted price for a single product.
     * If a discount exists for the product, it is applied; otherwise
     * the original price is returned.
     *
     * @param product the product to calculate discount for
     * @return the final price after discount application
     */
    @Override
    public BigDecimal calculateDiscount(Product product) {
        BigDecimal price = product.getTotalPrice();

        return discountRepository.getByProductId(product.getId())
                .map(discount -> applyDiscount(price, discount))
                .orElse(price);
    }

    /**
     * Retrieves the discount assigned to a product.
     *
     * @param productId the identifier of the product
     * @return the DiscountDto for the product
     * @throws DiscountNotFoundException if no discount exists for the product
     */
    @Override
    public Optional<DiscountDto> getDiscountForProduct(Long productId) {
        return discountRepository.getByProductId(productId).map(DiscountMapper::mapDiscountToDto);
    }

    /**
     * Applies a discount to a product price based on discount type.
     * Supports percentage and constant value discounts.
     *
     * @param productPrice the original product price
     * @param discount     the discount to apply
     * @return the final price after applying the discount
     */
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

    /**
     * Calculates the total price of a list of products with applied discounts.
     *
     * @param products the list of products to calculate total price for
     * @return the total discounted price of all products
     */
    @Override
    public BigDecimal calculateTotalCart(List<Product> products) {
        return products.stream()
                .map(this::calculateDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
