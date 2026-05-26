package service;

import dto.type.ComputerDto;

import java.util.List;

public interface ComputerService {
    ComputerDto createComputer(ComputerDto computerDto);

    ComputerDto removeComputer(Long id);

    ComputerDto updateComputer(Long id, ComputerDto computerDto);

    ComputerDto getComputerById(Long id);

    List<ComputerDto> getAllComputers();
}
