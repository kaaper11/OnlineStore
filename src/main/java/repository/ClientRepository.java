package repository;

import entity.client.Address;
import entity.client.Client;
import entity.client.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Client entities in memory.
 * It provides operations for creating, updating, retrieving, and deleting clients,
 * using a thread-safe set and an internal ID generator.
 */
public class ClientRepository {
    private final Set<Client> clients = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    public ClientRepository() {
        Client client = new Client(100L, "Kacper", "kacper60@wp.pl", "123",
                "123456789", new Address("Polska", "WWa", "Warszawska", "15-876", 2),
                Role.ADMIN);
        clients.add(client);
    }

    /**
     * Saves a client entity into the repository.
     *
     * @param client the client entity to be stored
     * @return the saved client instance
     */
    public Client save(Client client) {
        clients.add(client);
        client.setId(getNextId());
        return client;
    }

    /**
     * Deletes a client by its identifier.
     * If the client exists, it is removed from the repository.
     *
     * @param id the identifier of the client to be deleted
     * @return an Optional containing the removed client if found,
     *         otherwise an empty Optional
     */
    public Optional<Client> delete(Long id) {
        return getClientById(id)
                .map(client -> {
                    clients.remove(client);
                    return client;
                });
    }

    /**
     * Updates an existing client with new data.
     * The old client is removed and replaced with the updated one.
     *
     * @param id the identifier of the client to update
     * @param updatedClient the new client data to replace the existing one
     * @return an Optional containing the updated client if found,
     *         otherwise an empty Optional
     */
    public Optional<Client> update(Long id, Client updatedClient) {
        return getClientById(id)
                .map(client -> {
                    clients.remove(client);
                    clients.add(updatedClient);
                    return updatedClient;
                });
    }

    /**
     * Retrieves a client by its identifier.
     *
     * @param id the identifier of the client
     * @return an Optional containing the found client or empty if not found
     */
    public Optional<Client> getClientById(Long id) {
        return clients.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves a client by email address.
     *
     * @param email the email of the client
     * @return an Optional containing the found client or empty if not found
     */
    public Optional<Client> getClientByEmail(String email) {
        return clients.stream()
                .filter(client -> client.getEmail().equals(email))
                .findFirst();
    }

    /**
     * Retrieves all clients stored in the repository.
     *
     * @return a list of all clients
     */
    public List<Client> getAllComputers() {
        return clients.stream()
                .toList();
    }

    private Long getNextId() {
        return idCounter.getAndIncrement();
    }

    /**
     * Checks whether a client with the given email already exists in the repository.
     *
     * @param email the email to check
     * @return true if a client with the email exists, false otherwise
     */
    public boolean isClientExist(String email) {
        return getClientByEmail(email).isPresent();
    }
}
