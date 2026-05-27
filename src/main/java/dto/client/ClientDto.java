package dto.client;

public record ClientDto(String name, String email, String phone, AddressDto address) {
}
