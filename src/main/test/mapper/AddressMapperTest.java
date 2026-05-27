package mapper;

import dto.client.AddressDto;
import entity.client.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AddressMapperTest {

    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820",
            10);
    private final AddressDto addressDto = new AddressDto("Poland", "Warsaw", "Zlota", "15-820",
            10);

    @Test
    public void shouldMapAddressToDto() {
        AddressDto result = AddressMapper.mapAddressToDto(address);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(address);
    }

    @Test
    public void shouldMapAddressDtoToAddress() {
        Address result = AddressMapper.mapDtoToAddress(addressDto);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(addressDto);
    }
}
