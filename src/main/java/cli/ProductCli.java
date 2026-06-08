package cli;

import dto.discount.DiscountRequest;
import dto.product.request.ComputerRequestDto;
import dto.product.request.ElectronicsRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.request.SmartphoneRequestDto;
import entity.discount.DiscountType;
import exception.*;
import lombok.AllArgsConstructor;
import service.impl.DiscountServiceImpl;
import service.impl.ProductFacadeService;

import java.math.BigDecimal;
import java.util.Scanner;

@AllArgsConstructor
public class ProductCli {
    private final ProductFacadeService productFacadeService;
    private final DiscountServiceImpl discountService;
    private final Scanner scanner;

    public void showProductMenu(Long clientId) {
        try {
            while (true) {
                System.out.println("\nMenu Produktowe");
                System.out.println("1. Dodaj komputer");
                System.out.println("2. Dodaj smartfon");
                System.out.println("3. Dodaj elektronikę");
                System.out.println("4. Usuń produkt");
                System.out.println("5. Zaktualizuj ilość produktu");
                System.out.println("6. Zaktualizuj cene produktu");
                System.out.println("7. Przeglądaj produkty");
                System.out.println("8. Dodaj promocje");
                System.out.println("0. Wyjdź");
                System.out.print("Wybierz opcję: ");

                switch (scanner.nextLine()) {
                    case "1" -> addProduct("computer", clientId);
                    case "2" -> addProduct("smartphone", clientId);
                    case "3" -> addProduct("electronics", clientId);
                    case "4" -> removeProduct(clientId);
                    case "5" -> updateProductQuantity(clientId);
                    case "6" -> updateProductPrice(clientId);
                    case "7" -> getAllProducts();
                    case "8" -> addDiscount(clientId);
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Nieznana opcja.");
                }
            }
        } catch (NoPermissionsException | ProductTypeNotFoundException | ProductNotFoundException |
                 ValidationException e) {
            System.out.println(e.getMessage());
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void addProduct(String type, Long clientId) {
        System.out.print("Nazwa: ");
        String name = scanner.nextLine();
        System.out.print("Cena: ");
        BigDecimal price = TypeReaderCli.readBigDecimal(scanner);
        System.out.print("Ilość: ");
        int quantity = TypeReaderCli.readInt(scanner);

        ProductRequestDto dto = switch (type) {
            case "computer" -> new ComputerRequestDto(name, price, quantity);
            case "smartphone" -> new SmartphoneRequestDto(name, price, quantity);
            case "electronics" -> new ElectronicsRequestDto(name, price, quantity);
            default -> throw new ProductTypeNotFoundException();
        };

        productFacadeService.create(dto, clientId);
        System.out.println("Produkt dodany!");
    }

    private void removeProduct(Long clientId) {
        System.out.print("ID produktu: ");
        Long productId = TypeReaderCli.readLong(scanner);
        productFacadeService.removeProduct(productId, clientId);
        System.out.println("Produkt usunięty!");
    }

    private void updateProductQuantity(Long clientId) {
        System.out.println("ID produktu do aktualizacji ilośći magazynowej: ");
        Long productId = TypeReaderCli.readLong(scanner);
        System.out.println("Ilość: ");
        int quantity = TypeReaderCli.readInt(scanner);
        productFacadeService.updateProductQuantity(productId, clientId, quantity);
    }

    private void updateProductPrice(Long clientId) {
        System.out.println("ID produktu do aktualizacji ceny: ");
        Long productId = TypeReaderCli.readLong(scanner);
        System.out.println("Cena: ");
        BigDecimal price = TypeReaderCli.readBigDecimal(scanner);
        productFacadeService.updateProductPrice(productId, clientId, price);
    }

    private void getAllProducts() {
        System.out.println("Wszytskie produkty dostępne w sklepie: ");
        productFacadeService.getAllProducts().forEach(product ->
                System.out.println(product + discountService.formatProductWithDiscount(product)));
    }

    private void addDiscount(Long clientId) {
        System.out.println("=== Dodaj promocję ===");

        System.out.print("ID produktu: ");
        Long productId = scanner.nextLong();

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

        discountService.addDiscount(new DiscountRequest(productId, type, value), clientId);
        System.out.println("Dodano promocję!");
    }
}