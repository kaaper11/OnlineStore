package service.client;

import dto.client.LoginRequest;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.client.Client;
import exception.ClientAlreadyExists;
import exception.ClientNotFoundException;
import exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import mapper.ClientMapper;
import repository.CartRepository;
import repository.ClientRepository;
import validator.ClientValidator;

import java.util.List;

/**
 * Service implementation responsible for managing client-related operations.
 * It handles client creation, update, deletion, retrieval, and authentication,
 * while coordinating interactions with the client and cart repositories.
 */
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;

    /**
     * Creates a new client in the system.
     * The method validates input data, checks for duplicate emails,
     * persists the client, and initializes an empty cart for the new client.
     *
     * @param clientRequestDto the data required to create a new client
     * @return the created ClientResponseDto
     * @throws ClientAlreadyExists if a client with the given email already exists
     */
    @Override
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        ClientValidator.validate(clientRequestDto);

        if (clientRepository.isClientExist(clientRequestDto.email())) {
            throw new ClientAlreadyExists();
        }

        Client client = clientRepository.save(ClientMapper.mapDtoToClient(clientRequestDto,
                clientRepository.getNextId()));
        cartRepository.save(client.getId());
        return ClientMapper.mapClientToDto(client);
    }

    /**
     * Updates an existing client's data.
     *
     * @param id               the identifier of the client to update
     * @param clientRequestDto the new client data
     * @return the updated ClientResponseDto
     * @throws ClientNotFoundException if the client does not exist
     */
    @Override
    public ClientResponseDto updateClient(Long id, ClientRequestDto clientRequestDto) {
        Client client = clientRepository.update(id, ClientMapper.mapDtoToClient(clientRequestDto, id))
                .orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    /**
     * Removes a client from the system along with their cart.
     *
     * @param id the identifier of the client to remove
     * @return the removed client's data as ClientResponseDto
     * @throws ClientNotFoundException if the client does not exist
     */
    @Override
    public ClientResponseDto removeClient(Long id) {
        cartRepository.delete(id);
        Client client = clientRepository.delete(id).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    /**
     * Retrieves a client by their identifier.
     *
     * @param id the identifier of the client
     * @return the found ClientResponseDto
     * @throws ClientNotFoundException if the client does not exist
     */
    @Override
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.getClientById(id).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    /**
     * Retrieves a client by their email address.
     *
     * @param email the email of the client
     * @return the found ClientResponseDto
     * @throws ClientNotFoundException if the client does not exist
     */
    @Override
    public ClientResponseDto getClientByEmail(String email) {
        Client client = clientRepository.getClientByEmail(email).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    /**
     * Retrieves all clients in the system.
     *
     * @return a list of ClientResponseDto objects
     */
    @Override
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.getAllComputers().stream()
                .map(ClientMapper::mapClientToDto)
                .toList();
    }

    /**
     * Authenticates a client using email and password.
     *
     * @param loginRequest the login credentials
     * @return the authenticated ClientResponseDto
     * @throws ClientNotFoundException    if no client exists with the given email
     * @throws IncorrectPasswordException if the password is incorrect
     */
    @Override
    public ClientResponseDto loginClient(LoginRequest loginRequest) {
        Client client = clientRepository.getClientByEmail(loginRequest.email())
                .orElseThrow(ClientNotFoundException::new);

        if (!client.getPassword().equals(loginRequest.password())) {
            throw new IncorrectPasswordException();
        }

        return ClientMapper.mapClientToDto(client);
    }
}
