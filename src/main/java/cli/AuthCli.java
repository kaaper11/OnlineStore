package cli;

import dto.client.LoginRequest;
import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import exception.ClientAlreadyExists;
import exception.ClientNotFoundException;
import exception.IncorrectPasswordException;
import exception.ValidationException;
import lombok.RequiredArgsConstructor;
import service.client.ClientServiceImpl;

import java.util.Scanner;

@RequiredArgsConstructor
public class AuthCli {
    private final ClientServiceImpl clientService;
    private final Scanner scanner;

    public ClientResponseDto showAuthMenu() {
        try {
            while (true) {
                System.out.println("\nWitaj w sklepie");
                System.out.println("1. Zarejestruj się");
                System.out.println("2. Zaloguj się");
                System.out.println("0. Wyjście");
                System.out.print("> ");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> register();
                    case "2" -> {
                        return login();
                    }
                    case "0" -> System.exit(0);
                    default -> System.out.println("Nieznana opcja, spróbuj ponownie.");
                }
            }
        } catch (ClientNotFoundException | ClientAlreadyExists | IncorrectPasswordException | ValidationException e) {
            System.out.println("\n" + e.getMessage());
            return showAuthMenu();
        }
    }

    private void register() {
        System.out.println("\nRejestracja klienta");

        System.out.print("Podaj imię: ");
        String name = scanner.nextLine();

        System.out.print("Podaj email: ");
        String email = scanner.nextLine();

        System.out.print("Podaj hasło: ");
        String password = scanner.nextLine();

        System.out.print("Podaj numer telefonu: ");
        String phone = scanner.nextLine();

        System.out.print("Podaj kraj zamieszkania: ");
        String country = scanner.nextLine();

        System.out.print("Podaj miasto: ");
        String city = scanner.nextLine();

        System.out.print("Podaj ulicę: ");
        String street = scanner.nextLine();

        System.out.print("Podaj kod pocztowy (XX-XXX): ");
        String zip = scanner.nextLine();

        System.out.print("Podaj numer domu: ");
        int number = TypeReaderCli.readInt(scanner);

        AddressDto addressDto = new AddressDto(country, city, street, zip, number);
        ClientRequestDto clientRequestDto = new ClientRequestDto(name, email, password, phone, addressDto);
        clientService.createClient(clientRequestDto);

        System.out.println("Konto zostało pomyślnie utworzone!");
    }

    private ClientResponseDto login() {
        System.out.println("\nLogowanie");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        ClientResponseDto client = clientService.loginClient(new LoginRequest(email, password));
        System.out.println("Zalogowano jako: " + client.name());
        return client;
    }
}