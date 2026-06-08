package repository;

import entity.client.Address;
import entity.client.Client;
import entity.client.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ClientRepository {
    private final Set<Client> clients = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    public ClientRepository() {
        Client client = new Client(100L, "Kacper", "kacper60@wp.pl", "123",
                "123456789", new Address("Polska", "WWa", "Warszawska", "15-876", 2),
                Role.ADMIN);
        clients.add(client);
    }

    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    public Optional<Client> delete(Long id) {
        return getClientById(id)
                .map(client -> {
                    clients.remove(client);
                    return client;
                });
    }

    public Optional<Client> update(Long id, Client updatedClient) {
        return getClientById(id)
                .map(client -> {
                    clients.remove(client);
                    clients.add(updatedClient);
                    return updatedClient;
                });
    }

    public Optional<Client> getClientById(Long id) {
        return clients.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst();
    }

    public Optional<Client> getClientByEmail(String email) {
        return clients.stream()
                .filter(client -> client.getEmail().equals(email))
                .findFirst();
    }

    public List<Client> getAllComputers() {
        return clients.stream()
                .toList();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }

    public boolean isClientExist(String email) {
        return getClientByEmail(email).isPresent();
    }
}
