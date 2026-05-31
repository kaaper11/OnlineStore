package service.impl;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.product.type.Computer;
import exception.ComputerNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.ComputerMapper;
import repository.ComputerRepository;
import service.ComputerService;
import utils.ProductIdGenerator;

import java.util.List;

@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {
    private final ComputerRepository computerRepository;
    private final ProductIdGenerator productIdGenerator;

    @Override
    public ComputerResponseDto create(ComputerRequestDto computerRequestDto) {
        Computer computer = computerRepository.save(ComputerMapper.mapDtoToComputer(computerRequestDto,
                productIdGenerator.getNextProductId()));
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerResponseDto remove(Long id) {
        Computer computer = computerRepository.delete(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerResponseDto update(Long id, ComputerRequestDto computerRequestDto) {
        Computer computer = computerRepository.update(id, ComputerMapper.mapDtoToComputer(computerRequestDto, id))
                .orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerResponseDto getById(Long id) {
        Computer computer = computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public List<ComputerResponseDto> getAll() {
        return computerRepository.getAllComputers().stream()
                .map(ComputerMapper::mapComputerToDto)
                .toList();
    }
}
