package service.impl;

import dto.product.ComputerDto;
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
    public ComputerDto create(ComputerDto computerDto) {
        Computer computer = computerRepository.save(ComputerMapper.mapDtoToComputer(computerDto,
                computerRepository.getNextId()));
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerDto remove(Long id) {
        Computer computer = computerRepository.delete(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerDto update(Long id, ComputerDto computerDto) {
        Computer computer = computerRepository.update(id, ComputerMapper.mapDtoToComputer(computerDto, id))
                .orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerDto getById(Long id) {
        Computer computer = computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public List<ComputerDto> getAll() {
        return computerRepository.getAllComputers().stream()
                .map(ComputerMapper::mapComputerToDto)
                .toList();
    }
}
