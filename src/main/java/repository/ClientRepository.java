package repository;

import entity.client.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ClientRepository {
    private final Set<Client> clients = new HashSet<>();
    private Long idCounter = 0L;

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
                    return client;
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
        return idCounter++;
    }

}
