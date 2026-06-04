package repository;

import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryTest {

    @InjectMocks
    private ClientRepository clientRepository;

    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820", 10
    );

    private final Client client = new Client(1L, "name", "name@test.com","pass",
            "123456789", address, Role.ADMIN);

    private final Client updatedClient = new Client(1L, "name", "name@test.com", "pass",
            "987654321", address, Role.ADMIN);

    @Test
    public void shouldSaveClient() {
        Client result = clientRepository.save(client);

        assertThat(result).usingRecursiveComparison().isEqualTo(client);

        assertThat(clientRepository.getClientById(1L)).contains(client);
    }

    @Test
    public void shouldDeleteClient() {
        clientRepository.save(client);

        Optional<Client> result = clientRepository.delete(1L);

        assertThat(result).contains(client);

        assertThat(clientRepository.getClientById(1L)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyWhenDeletingNonExistingClient() {
        Optional<Client> result = clientRepository.delete(99L);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldUpdateClient() {
        clientRepository.save(client);

        Optional<Client> result = clientRepository.update(1L, updatedClient);

        assertThat(result).isPresent();
        assertThat(clientRepository.getClientById(1L)).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void shouldReturnEmptyWhenUpdatingNonExistingClient() {
        Optional<Client> result =
                clientRepository.update(99L, updatedClient);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGetClientById() {
        clientRepository.save(client);

        Optional<Client> result = clientRepository.getClientById(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(Optional.of(client));
    }

    @Test
    public void shouldGetClientByEmail() {
        clientRepository.save(client);

        Optional<Client> result = clientRepository.getClientByEmail("name@test.com");

        assertThat(result).usingRecursiveComparison().isEqualTo(Optional.of(client));
    }

    @Test
    public void shouldReturnAllClients() {
        clientRepository.save(client);

        List<Client> result = clientRepository.getAllComputers();

        assertThat(result).hasSize(2);
        assertThat(result.get(1)).isEqualTo(client);
    }

    @Test
    public void shouldGenerateNextId() {
        Long firstId = clientRepository.getNextId();
        Long secondId = clientRepository.getNextId();

        assertThat(firstId).isEqualTo(0L);
        assertThat(secondId).isEqualTo(1L);
    }

    @Test
    public void shouldTrueIfClientExists() {
        clientRepository.save(client);

        boolean result = clientRepository.isClientExist(client.getEmail());
        assertThat(result).isTrue();
    }

    @Test
    public void shouldFalseIfClientNotExists() {
        boolean result = clientRepository.isClientExist(client.getEmail());
        assertThat(result).isFalse();
    }
}