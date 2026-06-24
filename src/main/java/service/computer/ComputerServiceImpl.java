package service.computer;

import dto.product.request.ComputerRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Computer;
import entity.product.type.Product;
import exception.ClientNotFoundException;
import exception.ComputerNotFoundException;
import exception.NoPermissionsException;
import lombok.RequiredArgsConstructor;
import mapper.ComputerMapper;
import repository.ClientRepository;
import repository.productrepositories.ComputerRepository;
import validator.ProductValidator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service implementation responsible for managing Computer products.
 * It provides operations for creating, updating, deleting, retrieving,
 * and validating computer entities, while enforcing ADMIN-only access control.
 */
@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {
    private final ComputerRepository computerRepository;
    private final ClientRepository clientRepository;

    /**
     * Creates a new Computer product in the system.
     * The method validates input data, checks ADMIN permissions,
     * assigns a unique product ID, and persists the computer entity.
     *
     * @param dto      the product data used to create a computer
     * @param clientId the identifier of the client performing the operation
     * @return the created ComputerResponseDto
     * @throws ClientNotFoundException if the client does not exist
     * @throws NoPermissionsException  if the client is not an ADMIN
     */
    @Override
    public ComputerResponseDto create(ProductRequestDto dto, Long clientId) {
        ProductValidator.validate(dto);

        ComputerRequestDto computerRequestDto = (ComputerRequestDto) dto;

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.save(ComputerMapper.mapDtoToComputer(computerRequestDto));
        return ComputerMapper.mapComputerToDto(computer);
    }

    /**
     * Removes a Computer product from the system.
     * Only users with ADMIN role are allowed to perform this operation.
     *
     * @param id       the identifier of the computer to remove
     * @param clientId the identifier of the client performing the operation
     * @throws ClientNotFoundException   if the client does not exist
     * @throws NoPermissionsException    if the client is not an ADMIN
     * @throws ComputerNotFoundException if the computer does not exist
     */
    @Override
    public void remove(Long id, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        computerRepository.delete(id).orElseThrow(ComputerNotFoundException::new);
    }

    /**
     * Updates the price of a Computer product.
     * Only ADMIN users can perform this operation.
     *
     * @param id       the identifier of the computer
     * @param clientId the identifier of the client performing the operation
     * @param price    the new price value
     * @return the updated ComputerResponseDto
     * @throws ClientNotFoundException   if the client does not exist
     * @throws NoPermissionsException    if the client is not an ADMIN
     * @throws ComputerNotFoundException if the computer does not exist
     */
    @Override
    public ComputerResponseDto updatePrice(Long id, Long clientId, BigDecimal price) {
        ProductValidator.validatePrice(price);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);

        computer.setPrice(price);
        return ComputerMapper.mapComputerToDto(computer);
    }

    /**
     * Updates the quantity of a Computer product.
     * Only ADMIN users are allowed to modify product stock.
     *
     * @param id       the identifier of the computer
     * @param clientId the identifier of the client performing the operation
     * @param quantity the new quantity value
     * @return the updated ComputerResponseDto
     * @throws ClientNotFoundException   if the client does not exist
     * @throws NoPermissionsException    if the client is not an ADMIN
     * @throws ComputerNotFoundException if the computer does not exist
     */
    @Override
    public ComputerResponseDto updateQuantity(Long id, Long clientId, int quantity) {
        ProductValidator.validateQuantity(quantity);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Computer computer = computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);

        computer.setQuantity(quantity);
        return ComputerMapper.mapComputerToDto(computer);
    }

    /**
     * Retrieves a Computer product by its identifier.
     *
     * @param id the identifier of the computer
     * @return the ComputerResponseDto representing the found product
     * @throws ComputerNotFoundException if the computer does not exist
     */
    @Override
    public ComputerResponseDto getById(Long id) {
        Computer computer = computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);
        return ComputerMapper.mapComputerToDto(computer);
    }

    /**
     * Retrieves all Computer products from the system.
     *
     * @return a list of ComputerResponseDto objects
     */
    @Override
    public List<ComputerResponseDto> getAll() {
        return computerRepository.getAllComputers().stream()
                .map(ComputerMapper::mapComputerToDto)
                .toList();
    }

    /**
     * Checks whether a Computer with the given identifier exists.
     *
     * @param id the identifier of the computer
     * @return true if the computer exists, false otherwise
     */
    @Override
    public boolean exist(Long id) {
        return computerRepository.getComputerById(id).isPresent();
    }

    @Override
    public String getType() {
        return "computer";
    }

    @Override
    public Product getProductById(Long id) {
        return computerRepository.getComputerById(id).orElseThrow(ComputerNotFoundException::new);
    }
}
