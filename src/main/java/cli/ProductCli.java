package cli;

import discountformatter.DiscountFormatter;
import dto.discount.DiscountRequest;
import dto.product.request.ComputerRequestDto;
import dto.product.request.ElectronicsRequestDto;
import dto.product.request.SmartphoneRequestDto;
import entity.discount.DiscountType;
import exception.*;
import lombok.AllArgsConstructor;
import service.computer.ComputerServiceImpl;
import service.discount.DiscountServiceImpl;
import service.electronics.ElectronicsServiceImpl;
import service.productfacade.ProductFacadeServiceImpl;
import service.smartphone.SmartphoneServiceImpl;

import java.math.BigDecimal;
import java.util.Scanner;

@AllArgsConstructor
public class ProductCli {
    private final ProductFacadeServiceImpl productFacadeServiceImpl;
    private final ComputerServiceImpl computerService;
    private final SmartphoneServiceImpl smartphoneService;
    private final ElectronicsServiceImpl electronicsService;
    private final DiscountServiceImpl discountService;
    private final Scanner scanner;

    public void showProductMenu(Long clientId) {
        try {
            while (true) {
                System.out.println("\nMenu Produktowe");
                System.out.println("1. Dodaj komputer");
                System.out.println("2. Dodaj smartfon");
                System.out.println("3. Dodaj elektronikę");
                System.out.println("4. Usuń komputer");
                System.out.println("5. Usuń smatfon");
                System.out.println("6. Usuń ekektornikę");
                System.out.println("7. Zaktualizuj ilość komputera");
                System.out.println("8. Zaktualizuj ilość smatfona");
                System.out.println("9. Zaktualizuj ilość elektorniki");
                System.out.println("10. Zaktualizuj cene komputera");
                System.out.println("11. Zaktualizuj cene smatfona");
                System.out.println("12. Zaktualizuj cene elektorniki");
                System.out.println("13. Przeglądaj produkty");
                System.out.println("14. Dodaj promocje komputera");
                System.out.println("15. Dodaj promocje smartfona");
                System.out.println("16. Dodaj promocje elektorniki");
                System.out.println("0. Wyjdź");
                System.out.print("Wybierz opcję: ");

                switch (scanner.nextLine()) {
                    case "1" -> addProduct("computer", clientId);
                    case "2" -> addProduct("smartphone", clientId);
                    case "3" -> addProduct("electronics", clientId);
                    case "4" -> removeProduct("computer", clientId);
                    case "5" -> removeProduct("smartphone", clientId);
                    case "6" -> removeProduct("electronics", clientId);
                    case "7" -> updateProductQuantity("computer", clientId);
                    case "8" -> updateProductQuantity("smartphone", clientId);
                    case "9" -> updateProductQuantity("electronics", clientId);
                    case "10" -> updateProductPrice("computer", clientId);
                    case "11" -> updateProductPrice("smartphone", clientId);
                    case "12" -> updateProductPrice("electronics", clientId);
                    case "13" -> getAllProducts();
                    case "14" -> addDiscount("computer", clientId);
                    case "15" -> addDiscount("smartphone", clientId);
                    case "16" -> addDiscount("electronics", clientId);
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Nieznana opcja.");
                }
            }
        } catch (NoPermissionsException | ProductTypeNotFoundException | ProductNotFoundException |
                 ValidationException | DiscountForProductAlreadyExists | DiscountNotCorrectException e) {
            System.out.println(e.getMessage());
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    private void addProduct(String type, Long clientId) {
        System.out.print("Nazwa: ");
        String name = scanner.nextLine();
        System.out.print("Cena: ");
        BigDecimal price = TypeReaderCli.readBigDecimal(scanner);
        System.out.print("Ilość: ");
        int quantity = TypeReaderCli.readInt(scanner);

        switch (type) {
            case "computer" -> {
                ComputerRequestDto computerRequestDto = new ComputerRequestDto(name, price, quantity);
                computerService.create(computerRequestDto, clientId);
            }
            case "smartphone" -> {
                SmartphoneRequestDto smartphoneRequestDto = new SmartphoneRequestDto(name, price, quantity);
                smartphoneService.create(smartphoneRequestDto, clientId);
            }
            case "electronics" -> {
                ElectronicsRequestDto electronicsRequestDto = new ElectronicsRequestDto(name, price, quantity);
                electronicsService.create(electronicsRequestDto, clientId);
            }
            default -> throw new ProductTypeNotFoundException();
        }
//        productFacadeServiceImpl.createProduct(dto, clientId);
        System.out.println("Produkt dodany!");
    }

    private void removeProduct(String type, Long clientId) {
        System.out.print("ID produktu: ");
        Long productId = TypeReaderCli.readLong(scanner);

        switch (type) {
            case "computer" -> computerService.remove(productId, clientId);
            case "smartphone" -> smartphoneService.remove(productId, clientId);
            case "electronics" -> electronicsService.remove(productId, clientId);
            default -> throw new ProductTypeNotFoundException();
        }
//        productFacadeServiceImpl.removeProduct(productId, clientId);
        System.out.println("Produkt usunięty!");
    }

    private void updateProductQuantity(String type, Long clientId) {
        System.out.println("ID produktu do aktualizacji ilośći magazynowej: ");
        Long productId = TypeReaderCli.readLong(scanner);
        System.out.println("Ilość: ");
        int quantity = TypeReaderCli.readInt(scanner);

        switch (type) {
            case "computer" -> computerService.updateQuantity(productId, clientId, quantity);
            case "smartphone" -> smartphoneService.updateQuantity(productId, clientId, quantity);
            case "electronics" -> electronicsService.updateQuantity(productId, clientId, quantity);
            default -> throw new ProductTypeNotFoundException();
        }
//        productFacadeServiceImpl.updateProductQuantity(productId, clientId, quantity);
    }

    private void updateProductPrice(String type, Long clientId) {
        System.out.println("ID produktu do aktualizacji ceny: ");
        Long productId = TypeReaderCli.readLong(scanner);
        System.out.println("Cena: ");
        BigDecimal price = TypeReaderCli.readBigDecimal(scanner);

        switch (type) {
            case "computer" -> computerService.updatePrice(productId, clientId, price);
            case "smartphone" -> smartphoneService.updatePrice(productId, clientId, price);
            case "electronics" -> electronicsService.updatePrice(productId, clientId, price);
            default -> throw new ProductTypeNotFoundException();
        }
//        productFacadeServiceImpl.updateProductPrice(productId, clientId, price);
    }

    private void getAllProducts() {
        System.out.println("Wszytskie produkty dostępne w sklepie: ");
        productFacadeServiceImpl.getBaseProducts().forEach(product -> {
                    String discount = discountService.getDiscountForProduct(product.getId())
                            .map(DiscountFormatter::formatDiscount)
                            .orElse("");
                    System.out.println("- " + product + discount);
                }
        );
    }

    private void addDiscount(String productType, Long clientId) {
        System.out.println("Dodaj promocję");

        System.out.print("ID produktu: ");
        Long productId = TypeReaderCli.readLong(scanner);

        System.out.println("Typ rabatu:");
        System.out.println("1. Procentowy (%)");
        System.out.println("2. Stała kwota (zł)");
        System.out.print("Wybór: ");
        int typeChoice = TypeReaderCli.readInt(scanner);

        DiscountType type = switch (typeChoice) {
            case 1 -> DiscountType.PERCENT;
            case 2 -> DiscountType.CONSTANT;
            default -> throw new IllegalArgumentException("Nieprawidłowy wybór");
        };

        System.out.print("Wartość (" + (type == DiscountType.PERCENT ? "%" : "zł") + "): ");
        BigDecimal value = TypeReaderCli.readBigDecimal(scanner);

        switch (productType) {
            case "computer" -> discountService.addDiscount("computer",
                    new DiscountRequest(productId, type, value), clientId);
            case "smartphone" -> discountService.addDiscount("smartphone",
                    new DiscountRequest(productId, type, value), clientId);
            case "electronics" -> discountService.addDiscount("electronics",
                    new DiscountRequest(productId, type, value), clientId);
        }

        System.out.println("Dodano promocję!");
    }
}