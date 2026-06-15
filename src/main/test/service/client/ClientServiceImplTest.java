package service.client;

import dto.client.LoginRequest;
import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.cart.Cart;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import exception.ClientNotFoundException;
import exception.IncorrectPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CartRepository;
import repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private final AddressDto addressDto = new AddressDto("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private final Client client = new Client(1L, "name", "name@test.com", "pasS12%dd",
            "123456789", address, Role.ADMIN);

    private final Client updatedClient = new Client(1L, "name2", "name@test.com", "pasS12%dd",
            "987654321", address, Role.ADMIN);

    private final ClientRequestDto request = new ClientRequestDto("name3", "name@test.com",
            "pasS12%dd", "123456789", addressDto);

    @Test
    public void shouldCreateClient() {
        when(clientRepository.getNextId()).thenReturn(1L);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(cartRepository.save(anyLong())).thenReturn(any(Cart.class));

        ClientResponseDto result = clientService.createClient(request);

        assertThat(result).isNotNull();

        verify(clientRepository).getNextId();
        verify(clientRepository).save(any(Client.class));
        assertThat(result.email()).isEqualTo(request.email());
    }

    @Test
    public void shouldUpdateClient() {
        when(clientRepository.update(eq(1L), any(Client.class)))
                .thenReturn(Optional.of(client));

        ClientResponseDto result = clientService.updateClient(1L, request);

        assertThat(result).isNotNull();

        verify(clientRepository).update(eq(1L), any(Client.class));
        assertThat(result.email()).isEqualTo(request.email());
    }

    @Test
    public void shouldThrowWhenUpdatingNotExistingClient() {
        when(clientRepository.update(eq(1L), any(Client.class)))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(1L, request));
    }

    @Test
    public void shouldRemoveClient() {
        when(clientRepository.delete(1L)).thenReturn(Optional.of(client));

        ClientResponseDto result = clientService.removeClient(1L);

        assertThat(result).isNotNull();

        verify(clientRepository).delete(1L);
        assertThat(result.id()).isEqualTo(client.getId());
    }

    @Test
    public void shouldThrowWhenRemovingNotExistingClient() {
        when(clientRepository.delete(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.removeClient(1L));
    }

    @Test
    public void shouldGetClientById() {
        when(clientRepository.getClientById(1L)).thenReturn(Optional.of(client));

        ClientResponseDto result = clientService.getClientById(1L);

        assertThat(result).isNotNull();

        verify(clientRepository).getClientById(1L);
        assertThat(result.id()).isEqualTo(client.getId());
    }

    @Test
    public void shouldThrowWhenClientByIdNotFound() {
        when(clientRepository.getClientById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(1L));
    }

    @Test
    public void shouldGetClientByEmail() {
        when(clientRepository.getClientByEmail("name@test.com"))
                .thenReturn(Optional.of(client));

        ClientResponseDto result = clientService.getClientByEmail("name@test.com");

        assertThat(result).isNotNull();

        verify(clientRepository).getClientByEmail("name@test.com");
        assertThat(result.id()).isEqualTo(client.getId());
    }

    @Test
    public void shouldGetAllClients() {
        when(clientRepository.getAllComputers()).thenReturn(List.of(client, updatedClient));

        List<ClientResponseDto> result = clientService.getAllClients();

        assertThat(result).hasSize(2);

        verify(clientRepository).getAllComputers();
        assertThat(result.getFirst().id()).isEqualTo(client.getId());
    }

    @Test
    public void shouldLoginClientWhenPasswordMatches() {
        LoginRequest loginRequest = new LoginRequest("name@test.com", "pasS12%dd");

        when(clientRepository.getClientByEmail(anyString())).thenReturn(Optional.of(client));

        ClientResponseDto clientResponseDto = clientService.loginClient(loginRequest);

        assertThat(clientResponseDto).isNotNull();
        assertThat(clientResponseDto.id()).isEqualTo(client.getId());
    }

    @Test
    public void shouldThrowWhenPasswordDoesNotMatch() {
        LoginRequest loginRequest = new LoginRequest("name@test.com", "zlepass");

        when(clientRepository.getClientByEmail(anyString())).thenReturn(Optional.of(client));

        assertThrows(IncorrectPasswordException.class, () -> clientService.loginClient(loginRequest));
    }
}
