package service.impl;

import dto.product.request.ElectronicsRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Electronics;
import exception.ClientNotFoundException;
import exception.ElectronicsNotFoundException;
import exception.NoPermissionsException;
import lombok.RequiredArgsConstructor;
import mapper.ElectronicsMapper;
import repository.ClientRepository;
import repository.productrepositories.ElectronicsRepository;
import service.ElectronicsService;
import repository.productrepositories.idconfig.ProductIdGenerator;
import validator.ProductValidator;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class ElectronicsServiceImpl implements ElectronicsService {
    private final ElectronicsRepository electronicsRepository;
    private final ClientRepository clientRepository;

    @Override
    public ElectronicsResponseDto create(ProductRequestDto dto, Long clientId) {
        ProductValidator.validate(dto);

        ElectronicsRequestDto electronicsRequestDto = (ElectronicsRequestDto) dto;
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.save(ElectronicsMapper.mapDtoToElectronics(electronicsRequestDto,
                ProductIdGenerator.getNextProductId()));
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto remove(Long id, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.delete(id).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto updatePrice(Long id, Long clientId, BigDecimal price) {
        ProductValidator.validatePrice(price);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.updatePrice(id, price)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto updateQuantity(Long id, Long clientId, int quantity) {
        ProductValidator.validateQuantity(quantity);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.updateQuantity(id, quantity)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto getById(Long id) {
        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public boolean exist(Long id) {
        return electronicsRepository.getElectronicsById(id).isPresent();
    }

    @Override
    public boolean isInstance(ProductRequestDto dto) {
        return dto instanceof ElectronicsRequestDto;
    }

    @Override
    public List<ElectronicsResponseDto> getAll() {
        return electronicsRepository.getAllElectronics().stream()
                .map(ElectronicsMapper::mapElectronicsToDto)
                .toList();
    }
}
