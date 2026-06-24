package service.client;

import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import dto.client.LoginRequest;
import exception.ClientAlreadyExists;
import exception.ClientNotFoundException;
import exception.IncorrectPasswordException;
import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CartRepository;
import repository.ClientRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ClientServiceImplTestIT {

    private ClientService clientService;

    private ClientRequestDto clientRequestDto;

    @BeforeEach
    void setUp() {
        ClientRepository clientRepository = new ClientRepository();
        CartRepository cartRepository = new CartRepository();

        clientService = new ClientServiceImpl(clientRepository, cartRepository);

        AddressDto addressDto = new AddressDto("Polska", "Wwa", "Zlota", "17-873", 10);
        clientRequestDto = new ClientRequestDto("Kacper", "kacper40@wp.pl",
                "Cos123%dd", "123456789", addressDto);
    }

    @Test
    void shouldCreateNewClient() {
        ClientResponseDto client = clientService.createClient(clientRequestDto);

        assertThat(client).isNotNull();
        assertThat(client).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(clientRequestDto);
    }

    @Test
    void shouldThrowWhenClientAlreadyExists() {
        clientService.createClient(clientRequestDto);
        assertThatExceptionOfType(ClientAlreadyExists.class)
                .isThrownBy(() -> clientService.createClient(clientRequestDto));
    }

    @Test
    void shouldThrowWhenEmailInvalid() {
        ClientRequestDto invalidDto = new ClientRequestDto(
                "Jan Kowalski", "niepoprawnyelmail", "Password1!", "123456789",
                new AddressDto("Polska", "Warszawa", "Marszałkowska", "00-001", 1)
        );
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> clientService.createClient(invalidDto));
    }

    @Test
    void shouldThrowWhenPasswordTooWeak() {
        ClientRequestDto invalidDto = new ClientRequestDto(
                "Jan Kowalski", "jan@example.com", "password", "123456789",
                new AddressDto("Polska", "Warszawa", "Marszałkowska", "00-001", 1)
        );
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> clientService.createClient(invalidDto));
    }

    @Test
    void shouldThrowWhenZipInvalid() {
        ClientRequestDto invalidDto = new ClientRequestDto(
                "Jan Kowalski", "jan@example.com", "Password1!", "123456789",
                new AddressDto("Polska", "Warszawa", "Marszałkowska", "00001", 1)
        );
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> clientService.createClient(invalidDto));
    }

    @Test
    void shouldGetClientById() {
        ClientResponseDto created = clientService.createClient(clientRequestDto);
        ClientResponseDto found = clientService.getClientById(created.id());
        assertThat(found.name()).isEqualTo("Kacper");
    }

    @Test
    void shouldThrowWhenClientNotFound() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> clientService.getClientById(99L));
    }

    @Test
    void shouldRemoveClient() {
        ClientResponseDto created = clientService.createClient(clientRequestDto);
        ClientResponseDto removed = clientService.removeClient(created.id());
        assertThat(removed.name()).isEqualTo("Kacper");
    }

    @Test
    void shouldThrowWhenRemovingNonExistentClient() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> clientService.removeClient(99L));
    }

    @Test
    void shouldLoginClient() {
        clientService.createClient(clientRequestDto);
        ClientResponseDto logged = clientService.loginClient(
                new LoginRequest("kacper40@wp.pl", "Cos123%dd")
        );
        assertThat(logged.name()).isEqualTo("Kacper");
    }

    @Test
    void shouldThrowWhenPasswordIncorrect() {
        clientService.createClient(clientRequestDto);
        assertThatExceptionOfType(IncorrectPasswordException.class)
                .isThrownBy(() -> clientService.loginClient(
                        new LoginRequest("kacper40@wp.pl", "ZleHaslo1!")
                ));
    }

    @Test
    void shouldThrowWhenEmailNotFound() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> clientService.loginClient(
                        new LoginRequest("nieznany@example.com", "Cos123%dd")
                ));
    }

    @Test
    void shouldUpdateClient() {
        ClientResponseDto created = clientService.createClient(clientRequestDto);

        ClientRequestDto updatedDto = new ClientRequestDto(
                "Zmiana", "zmiana@example.com", "Password1!", "987654321",
                new AddressDto("Polska", "Kraków", "Krakowska", "30-001", 5)
        );

        ClientResponseDto updated = clientService.updateClient(created.id(), updatedDto);
        assertThat(updated.name()).isEqualTo("Zmiana");
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentClient() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> clientService.updateClient(99L, clientRequestDto));
    }
}
