package dto.client;

public record ClientRequestDto(String name, String email, String phone, AddressDto address) {
}
