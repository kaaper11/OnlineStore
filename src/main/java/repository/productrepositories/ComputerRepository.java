package repository.productrepositories;

import entity.product.type.Computer;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Computer entities in memory.
 * It provides basic CRUD-like operations such as saving, retrieving,
 * updating, and deleting computer objects using a thread-safe set.
 */
public final class ComputerRepository {
    private final Set<Computer> computers = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    /**
     * Saves a computer entity into the repository.
     *
     * @param computer the computer entity to be stored
     * @return the saved computer instance
     */
    public Computer save(Computer computer) {
        computers.add(computer);
        return computer;
    }

    /**
     * Deletes a computer by its identifier.
     * If the computer exists, it is removed from the repository.
     *
     * @param id the identifier of the computer to be deleted
     * @return an Optional containing the removed computer if it existed,
     * otherwise an empty Optional
     */
    public Optional<Computer> delete(Long id) {
        return getComputerById(id)
                .map(computer -> {
                    computers.remove(computer);
                    return computer;
                });
    }

    /**
     * Retrieves a computer by its identifier.
     *
     * @param id the identifier of the computer
     * @return an Optional containing the found computer or empty if not found
     */
    public Optional<Computer> getComputerById(Long id) {
        return computers.stream()
                .filter(computer -> computer.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves all computers stored in the repository.
     *
     * @return a list of all computers
     */
    public List<Computer> getAllComputers() {
        return computers.stream()
                .toList();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }
}
