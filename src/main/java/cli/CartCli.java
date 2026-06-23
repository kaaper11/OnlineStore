package cli;

import discountformatter.DiscountFormatter;
import dto.cart.CartDto;
import dto.productconfig.ComputerConfig;
import dto.productconfig.ElectonicsConfig;
import dto.productconfig.ProductConfig;
import dto.productconfig.SmartphoneConfig;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import exception.CartEmptyException;
import exception.CartNotFoundException;
import exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import service.cart.CartServiceImpl;
import service.discount.DiscountServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class CartCli {
    private final CartServiceImpl cartService;
    private final DiscountServiceImpl discountService;
    private final Scanner scanner;

    public void run(Long clientId) {
        try {
            while (true) {
                System.out.println("\nKoszyk");
                System.out.println("1. Pokaż koszyk");
                System.out.println("2. Dodaj komputer do koszyka");
                System.out.println("3. Dodaj smartfon do koszyka");
                System.out.println("4. Dodaj elektronikę do koszyka");
                System.out.println("0. Wyjdź");
                System.out.print("Wybierz opcję: ");

                switch (scanner.nextLine()) {
                    case "1" -> showCart(clientId);
                    case "2" -> addComputer(clientId);
                    case "3" -> addSmartphone(clientId);
                    case "4" -> addElectronics(clientId);
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Nieznana opcja.");
                }
            }
        } catch (CartEmptyException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (CartNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (ClassCastException e) {
            System.out.println("Konfigurowałeś obiekt nieodpowiedniego typu!");
        }
    }

    private void showCart(Long clientId) {
        CartDto cart = cartService.getCartByClientId(clientId);
        System.out.println("\nTwój koszyk");
        cart.products().forEach(product -> {
                    String discount = discountService.getDiscountForProduct(product.getId())
                            .map(DiscountFormatter::formatDiscount)
                            .orElse("");
                    System.out.println("- " + product + discount);
                }
        );
    }

    private void addComputer(Long clientId) {
        System.out.print("ID komputera: ");
        Long productId = TypeReaderCli.readLong(scanner);

        System.out.println("Procesor " + Arrays.toString(Processor.values()));
        Processor processor = TypeReaderCli.readProcessor(scanner);

        System.out.println("RAM: " + Arrays.toString(Ram.values()));
        Ram ram = TypeReaderCli.readRam(scanner);

        System.out.println("ROM: " + Arrays.toString(Rom.values()));
        Rom rom = TypeReaderCli.readRom(scanner);

        System.out.println("Karta graficzna: " + Arrays.toString(GraphicCard.values()));
        GraphicCard graphicCard = TypeReaderCli.readGraphicCard(scanner);

        ProductConfig config = new ComputerConfig(processor, ram, rom, graphicCard);
        cartService.addProductToCart("computer", clientId, productId, config);
        System.out.println("Komputer dodany do koszyka!");
    }

    private void addSmartphone(Long clientId) {
        System.out.print("ID smartfona: ");
        Long productId = TypeReaderCli.readLong(scanner);

        System.out.println("Kolor: " + Arrays.toString(SmartphoneColorType.values()));
        SmartphoneColorType color = TypeReaderCli.readColor(scanner);

        System.out.println("Bateria: " + Arrays.toString(Battery.values()));
        Battery battery = TypeReaderCli.readBattery(scanner);

        System.out.println("Akcesoria (wpisuj kolejno, pusta linia = koniec): ");
        System.out.println("Dostępne: " + Arrays.toString(Accessory.values()));
        List<Accessory> accessories = new ArrayList<>();
        while (true) {
            String line = scanner.nextLine();
            if (line.isBlank()) break;
            try {
                accessories.add(Accessory.valueOf(line));
            } catch (IllegalArgumentException e) {
                System.out.println("Nieznane akcesorium, spróbuj ponownie.");
            }
        }

        ProductConfig config = new SmartphoneConfig(color, battery, accessories);
        cartService.addProductToCart("smartphone", clientId, productId, config);
        System.out.println("Smartfon dodany do koszyka!");
    }

    private void addElectronics(Long clientId) {
        System.out.print("ID produktu: ");
        Long productId = TypeReaderCli.readLong(scanner);

        cartService.addProductToCart("electronics", clientId, productId, new ElectonicsConfig());
        System.out.println("Produkt dodany do koszyka!");
    }
}