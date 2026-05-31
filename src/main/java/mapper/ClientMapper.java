package mapper;

import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.client.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientMapper {
//
//    public static ClientRequestDto mapClientToDto(Client client) {
//        return new ClientRequestDto(client.getName(), client.getEmail(), client.getPhone(),
//                AddressMapper.mapAddressToDto(client.getAddress()));
//    }

    public static Client mapDtoToClient(ClientRequestDto clientRequestDto, Long id) {
        return new Client(id, clientRequestDto.name(), clientRequestDto.email(), clientRequestDto.phone(),
                AddressMapper.mapDtoToAddress(clientRequestDto.address()));
    }

    public static ClientResponseDto mapClientToDto(Client client) {
        return new ClientResponseDto(client.getId(), client.getName(), client.getEmail(), client.getPhone(),
                AddressMapper.mapAddressToDto(client.getAddress()));
    }
}
