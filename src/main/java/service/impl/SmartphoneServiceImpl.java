package service.impl;

import dto.product.request.ProductRequestDto;
import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Smartphone;
import exception.ClientNotFoundException;
import exception.NoPermissionsException;
import exception.SmartphoneNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.SmartphoneMapper;
import repository.ClientRepository;
import repository.productrepositories.SmartphoneRepository;
import service.SmartphoneService;
import repository.productrepositories.idconfig.ProductIdGenerator;
import validator.ProductValidator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service implementation responsible for managing Smartphone products.
 * It provides functionality for creating, updating, deleting, retrieving,
 * and validating smartphone entities, while enforcing ADMIN-only access control.
 */
@RequiredArgsConstructor
public class SmartphoneServiceImpl implements SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;
    private final ClientRepository clientRepository;

    @Override
    public SmartphoneResponseDto create(ProductRequestDto dto, Long clientId) {
        ProductValidator.validate(dto);

        SmartphoneRequestDto smartphoneDto = (SmartphoneRequestDto) dto;

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Smartphone smartphone = smartphoneRepository.save(SmartphoneMapper.mapDtoToSmartphone(smartphoneDto,
                ProductIdGenerator.getNextProductId()));
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneResponseDto remove(Long id, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }
        Smartphone smartphone = smartphoneRepository.delete(id).orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public boolean exist(Long id) {
        return smartphoneRepository.getSmartphoneById(id).isPresent();
    }

    @Override
    public boolean isInstance(ProductRequestDto dto) {
        return dto instanceof SmartphoneRequestDto;
    }

    @Override
    public SmartphoneResponseDto updatePrice(Long id, Long clientId, BigDecimal price) {
        ProductValidator.validatePrice(price);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Smartphone smartphone = smartphoneRepository.updateSmartphonePrice(id, price)
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneResponseDto updateQuantity(Long id, Long clientId, int quantity) {
        ProductValidator.validateQuantity(quantity);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Smartphone smartphone = smartphoneRepository.updateSmartphoneQuantity(id, quantity)
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneResponseDto getById(Long id) {
        Smartphone smartphone = smartphoneRepository.getSmartphoneById(id)
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public List<SmartphoneResponseDto> getAll() {
        return smartphoneRepository.getAllSmartphones().stream()
                .map(SmartphoneMapper::mapSmartphoneToDto)
                .toList();
    }
}
