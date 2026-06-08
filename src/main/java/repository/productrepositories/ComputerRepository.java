package repository.productrepositories;

import entity.product.type.Computer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ComputerRepository {
    private final Set<Computer> computers = ConcurrentHashMap.newKeySet();

    public Computer save(Computer computer) {
        computers.add(computer);
        return computer;
    }

    public Optional<Computer> delete(Long id) {
        return getComputerById(id)
                .map(computer -> {
                    computers.remove(computer);
                    return computer;
                });
    }

    public Optional<Computer> getComputerById(Long id) {
        return computers.stream()
                .filter(computer -> computer.getId().equals(id))
                .findFirst();
    }

    public List<Computer> getAllComputers() {
        return computers.stream()
                .toList();
    }

    public Optional<Computer> updateComputerPrice(Long id, BigDecimal price) {
        return getComputerById(id)
                .map(computer -> {
                    computer.setPrice(price);
                    return computer;
                });
    }

    public Optional<Computer> updateComputerQuantity(Long id, int quantity) {
        return getComputerById(id)
                .map(computer -> {
                    computer.setQuantity(quantity);
                    return computer;
                });
    }
}
