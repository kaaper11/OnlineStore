package repository.productrepositories;

import entity.product.type.Electronics;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Electronics entities in memory.
 * It provides basic CRUD-like operations such as saving, retrieving,
 * updating, and deleting electronics objects using a thread-safe set.
 */
public class ElectronicsRepository {
    private final Set<Electronics> electronicsSet = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    /**
     * Saves an electronics entity into the repository.
     *
     * @param electronics the electronics entity to be stored
     * @return the saved electronics instance
     */
    public Electronics save(Electronics electronics) {
        electronicsSet.add(electronics);
        return electronics;
    }

    /**
     * Deletes an electronics entity by its identifier.
     * If the entity exists, it is removed from the repository.
     *
     * @param id the identifier of the electronics entity to be deleted
     * @return an Optional containing the removed electronics if found,
     * otherwise an empty Optional
     */
    public Optional<Electronics> delete(Long id) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronicsSet.remove(electronics);
                    return electronics;
                });
    }

    /**
     * Retrieves an electronics entity by its identifier.
     *
     * @param id the identifier of the electronics entity
     * @return an Optional containing the found electronics or empty if not found
     */
    public Optional<Electronics> getElectronicsById(Long id) {
        return electronicsSet.stream()
                .filter(electronics -> electronics.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves all electronics entities stored in the repository.
     *
     * @return a list of all electronics entities
     */
    public List<Electronics> getAllElectronics() {
        return electronicsSet.stream()
                .toList();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }
}
