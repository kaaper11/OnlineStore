package mapper;

import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.client.Client;
import entity.client.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting between Client entities
 * and Client DTO objects.
 * This class is non-instantiable and provides static methods for
 * transforming client data between persistence and transport layers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientMapper {

    public static Client mapDtoToClient(ClientRequestDto clientRequestDto, Long id) {
        return new Client(id, clientRequestDto.name(), clientRequestDto.email(), clientRequestDto.password(),
                clientRequestDto.phone(), AddressMapper.mapDtoToAddress(clientRequestDto.address()),
                Role.USER);
    }

    public static ClientResponseDto mapClientToDto(Client client) {
        return new ClientResponseDto(client.getId(), client.getName(), client.getEmail(), client.getPassword(),
                client.getPhone(), AddressMapper.mapAddressToDto(client.getAddress()));
    }
}
