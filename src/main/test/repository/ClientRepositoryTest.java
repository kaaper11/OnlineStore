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

    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820", 10
    );

    private static final Client CLIENT = new Client(1L, "name", "name@test.com","pass",
            "123456789", ADDRESS, Role.ADMIN);

    private static final Client UPDATED_CLIENT = new Client(1L, "name", "name@test.com", "pass",
            "987654321", ADDRESS, Role.ADMIN);

    @Test
    public void shouldSaveClient() {
        Client result = clientRepository.save(CLIENT);

        assertThat(result).usingRecursiveComparison().isEqualTo(CLIENT);

        assertThat(clientRepository.getClientById(1L)).contains(CLIENT);
    }

    @Test
    public void shouldDeleteClient() {
        clientRepository.save(CLIENT);

        final var result = clientRepository.delete(1L);

        assertThat(result).contains(CLIENT);

        assertThat(clientRepository.getClientById(1L)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyWhenDeletingNonExistingClient() {
        Optional<Client> result = clientRepository.delete(99L);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldUpdateClient() {
        clientRepository.save(CLIENT);

        final var result = clientRepository.update(1L, UPDATED_CLIENT);

        assertThat(result).isPresent();
        assertThat(clientRepository.getClientById(1L)).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void shouldReturnEmptyWhenUpdatingNonExistingClient() {
        final var result =
                clientRepository.update(99L, UPDATED_CLIENT);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGetClientById() {
        clientRepository.save(CLIENT);

        final var result = clientRepository.getClientById(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(Optional.of(CLIENT));
    }

    @Test
    public void shouldGetClientByEmail() {
        clientRepository.save(CLIENT);

        final var result = clientRepository.getClientByEmail("name@test.com");

        assertThat(result).usingRecursiveComparison().isEqualTo(Optional.of(CLIENT));
    }

    @Test
    public void shouldReturnAllClients() {
        clientRepository.save(CLIENT);

        List<Client> result = clientRepository.getAllComputers();

        assertThat(result).hasSize(2);
        assertThat(result.get(1)).isEqualTo(CLIENT);
    }

    @Test
    public void shouldTrueIfClientExists() {
        clientRepository.save(CLIENT);

        boolean result = clientRepository.isClientExist(CLIENT.getEmail());
        assertThat(result).isTrue();
    }

    @Test
    public void shouldFalseIfClientNotExists() {
        boolean result = clientRepository.isClientExist(CLIENT.getEmail());
        assertThat(result).isFalse();
    }
}