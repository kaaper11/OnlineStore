package repository.productrepositories;

import entity.product.type.Smartphone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository responsible for managing Smartphone entities in memory.
 * It provides basic CRUD-like operations such as saving, retrieving,
 * updating, and deleting smartphone objects using a thread-safe set.
 */
public class SmartphoneRepository {
    private final Set<Smartphone> smartphones = ConcurrentHashMap.newKeySet();

    /**
     * Saves a smartphone entity into the repository.
     *
     * @param smartphone the smartphone entity to be stored
     * @return the saved smartphone instance
     */
    public Smartphone save(Smartphone smartphone) {
        smartphones.add(smartphone);
        return smartphone;
    }

    /**
     * Deletes a smartphone entity by its identifier.
     * If the entity exists, it is removed from the repository.
     *
     * @param id the identifier of the smartphone to be deleted
     * @return an Optional containing the removed smartphone if found,
     * otherwise an empty Optional
     */
    public Optional<Smartphone> delete(Long id) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphones.remove(smartphone);
                    return smartphone;
                });
    }

    /**
     * Retrieves a smartphone entity by its identifier.
     *
     * @param id the identifier of the smartphone
     * @return an Optional containing the found smartphone or empty if not found
     */
    public Optional<Smartphone> getSmartphoneById(Long id) {
        return smartphones.stream()
                .filter(smartphone -> smartphone.getId().equals(id))
                .findFirst();
    }

    /**
     * Retrieves all smartphone entities stored in the repository.
     *
     * @return a list of all smartphones
     */
    public List<Smartphone> getAllSmartphones() {
        return smartphones.stream()
                .toList();
    }

    /**
     * Updates the price of a smartphone with the given identifier.
     *
     * @param id    the identifier of the smartphone to update
     * @param price the new price to set
     * @return an Optional containing the updated smartphone if found,
     * otherwise an empty Optional
     */
    public Optional<Smartphone> updateSmartphonePrice(Long id, BigDecimal price) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphone.setPrice(price);
                    return smartphone;
                });
    }

    /**
     * Updates the quantity of a smartphone with the given identifier.
     *
     * @param id       the identifier of the smartphone to update
     * @param quantity the new quantity to set
     * @return an Optional containing the updated smartphone if found,
     * otherwise an empty Optional
     */
    public Optional<Smartphone> updateSmartphoneQuantity(Long id, int quantity) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphone.setQuantity(quantity);
                    return smartphone;
                });
    }
}
