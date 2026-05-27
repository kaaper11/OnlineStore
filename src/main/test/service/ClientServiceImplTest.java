package service;

import dto.client.AddressDto;
import dto.client.ClientDto;
import entity.client.Address;
import entity.client.Client;
import exception.ClientNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;
import service.impl.ClientServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private final AddressDto addressDto = new AddressDto("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private final Client client = new Client(1L, "name", "name@test.com", "123456789",
            address);

    private final Client updatedClient = new Client(1L, "name2", "name@test.com", "987654321",
            address);

    private final ClientDto request = new ClientDto("name3", "name@test.com", "123456789",
            addressDto);

    @Test
    public void shouldCreateClient() {
        when(clientRepository.getNextId()).thenReturn(1L);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDto result = clientService.createClient(request);

        assertThat(result).isNotNull();

        verify(clientRepository).getNextId();
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    public void shouldUpdateClient() {
        when(clientRepository.update(eq(1L), any(Client.class)))
                .thenReturn(Optional.of(client));

        ClientDto result = clientService.updateClient(1L, request);

        assertThat(result).isNotNull();

        verify(clientRepository).update(eq(1L), any(Client.class));
    }

    @Test
    public void shouldThrowWhenUpdatingNotExistingClient() {
        when(clientRepository.update(eq(1L), any(Client.class)))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientService.updateClient(1L, request));
    }

    @Test
    public void shouldRemoveClient() {
        when(clientRepository.delete(1L)).thenReturn(Optional.of(client));

        ClientDto result = clientService.removeClient(1L);

        assertThat(result).isNotNull();

        verify(clientRepository).delete(1L);
    }

    @Test
    public void shouldThrowWhenRemovingNotExistingClient() {
        when(clientRepository.delete(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientService.removeClient(1L));
    }

    @Test
    public void shouldGetClientById() {
        when(clientRepository.getClientById(1L)).thenReturn(Optional.of(client));

        ClientDto result = clientService.getClientById(1L);

        assertThat(result).isNotNull();

        verify(clientRepository).getClientById(1L);
    }

    @Test
    public void shouldThrowWhenClientByIdNotFound() {
        when(clientRepository.getClientById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientService.getClientById(1L));
    }

    @Test
    public void shouldGetClientByEmail() {
        when(clientRepository.getClientByEmail("name@test.com"))
                .thenReturn(Optional.of(client));

        ClientDto result = clientService.getClientByEmail("name@test.com");

        assertThat(result).isNotNull();

        verify(clientRepository).getClientByEmail("name@test.com");
    }

    @Test
    public void shouldGetAllClients() {
        when(clientRepository.getAllComputers()).thenReturn(List.of(client, updatedClient));

        List<ClientDto> result = clientService.getAllClients();

        assertThat(result).hasSize(2);

        verify(clientRepository).getAllComputers();
    }
}
