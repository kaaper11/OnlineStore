package service.impl;

import dto.product.request.ComputerRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Computer;
import exception.ClientNotFoundException;
import exception.ComputerNotFoundException;
import exception.NoPermissionsException;
import lombok.RequiredArgsConstructor;
import mapper.ComputerMapper;
import repository.ClientRepository;
import repository.productrepositories.ComputerRepository;
import service.ComputerService;
import repository.productrepositories.idconfig.ProductIdGenerator;
import validator.ProductValidator;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {
    private final ComputerRepository computerRepository;
    private final ClientRepository clientRepository;

    @Override
    public ComputerResponseDto create(ProductRequestDto dto, Long clientId) {
        ProductValidator.validate(dto);

        ComputerRequestDto computerRequestDto = (ComputerRequestDto) dto;

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.save(ComputerMapper.mapDtoToComputer(computerRequestDto,
                ProductIdGenerator.getNextProductId()));
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerResponseDto remove(Long id, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.delete(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerResponseDto updatePrice(Long id, Long clientId, BigDecimal price) {
        ProductValidator.validatePrice(price);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.updateComputerPrice(id, price)
                .orElseThrow(ComputerNotFoundException::new);

        return ComputerMapper.mapComputerToDto(computer);
    }

    @Override
    public ComputerResponseDto updateQuantity(Long id, Long clientId, int quantity) {
        ProductValidator.validateQuantity(quantity);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.updateComputerQuantity(id, quantity)
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

    @Override
    public boolean exist(Long id) {
        return computerRepository.getComputerById(id).isPresent();
    }

    @Override
    public boolean isInstance(ProductRequestDto dto) {
        return dto instanceof ComputerRequestDto;
    }
}
