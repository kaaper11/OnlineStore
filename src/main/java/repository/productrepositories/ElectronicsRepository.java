package repository.productrepositories;

import entity.product.type.Electronics;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository responsible for managing Electronics entities in memory.
 * It provides basic CRUD-like operations such as saving, retrieving,
 * updating, and deleting electronics objects using a thread-safe set.
 */
public class ElectronicsRepository {
    private final Set<Electronics> electronicsSet = ConcurrentHashMap.newKeySet();

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

    /**
     * Updates the price of an electronics entity with the given identifier.
     *
     * @param id    the identifier of the electronics entity to update
     * @param price the new price to set
     * @return an Optional containing the updated electronics if found,
     * otherwise an empty Optional
     */
    public Optional<Electronics> updatePrice(Long id, BigDecimal price) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronics.setPrice(price);
                    return electronics;
                });
    }

    /**
     * Updates the quantity of an electronics entity with the given identifier.
     *
     * @param id       the identifier of the electronics entity to update
     * @param quantity the new quantity to set
     * @return an Optional containing the updated electronics if found,
     * otherwise an empty Optional
     */
    public Optional<Electronics> updateQuantity(Long id, Integer quantity) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronics.setQuantity(quantity);
                    return electronics;
                });
    }
}
