package mapper;

import dto.client.AddressDto;
import entity.client.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting between Address entity
 * and AddressDto objects.
 * This class is not instantiable and provides only static mapping methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddressMapper {

    public static AddressDto mapAddressToDto(Address address) {
        return new AddressDto(address.getCountry(), address.getCity(), address.getStreet(), address.getZip(),
                address.getNumber());
    }

    public static Address mapDtoToAddress(AddressDto addressDto) {
        return new Address(addressDto.country(), addressDto.city(), addressDto.street(), addressDto.zip(),
                addressDto.number());
    }
}
