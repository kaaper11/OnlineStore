package service.impl;

import dto.type.ComputerDto;
import entity.product.type.Computer;
import exception.ComputerNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.ComputerMapper;
import repository.ComputerRepository;
import service.ComputerService;

import java.util.List;

@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {
    private final ComputerRepository computerRepository;

    @Override
    public ComputerDto createComputer(ComputerDto computerDto) {
        Computer computer = computerRepository.save(ComputerMapper.mapDtoToComputer(computerDto,
                computerRepository.getNextId()));
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerDto removeComputer(Long id) {
        Computer computer = computerRepository.delete(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerDto updateComputer(Long id, ComputerDto computerDto) {
        Computer computer = computerRepository.update(id, ComputerMapper.mapDtoToComputer(computerDto, id))
                .orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerDto getComputerById(Long id) {
        Computer computer = computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public List<ComputerDto> getAllComputers() {
        return computerRepository.getAllComputers().stream()
                .map(ComputerMapper::mapComputerToDto)
                .toList();
    }
}
