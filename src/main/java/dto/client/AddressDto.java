package dto.client;

public record AddressDto(
        String country,

        String city,

        String street,

        String zip,

        int number) {
}
