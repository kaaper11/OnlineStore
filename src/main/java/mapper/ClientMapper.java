package mapper;

import dto.client.ClientDto;
import entity.client.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientMapper {

    public static ClientDto mapClientToDto(Client client) {
        return new ClientDto(client.getName(), client.getEmail(), client.getPhone(),
                AddressMapper.mapAddressToDto(client.getAddress()));
    }

    public static Client mapDtoToClient(ClientDto clientDto, Long id) {
        return new Client(id, clientDto.name(), clientDto.email(), clientDto.phone(),
                AddressMapper.mapDtoToAddress(clientDto.address()));
    }
}
