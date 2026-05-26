package repository;

import entity.product.type.Computer;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ComputerRepository {
    private final Set<Computer> computers = new HashSet<>();
    private Long idCounter = 0L;

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

    public Optional<Computer> update(Long id, Computer updatedComputer) {
//        if (computers.removeIf(computer -> computer.getId().equals(id))) {
//            computers.add(updatedComputer);
//            return Optional.of(updatedComputer);
//        }
        return getComputerById(id)
                .map(computer -> {
                    computers.remove(computer);
                    computers.add(updatedComputer);
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

    public Long getNextId() {
        return idCounter++;
    }
}
