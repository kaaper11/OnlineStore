package entity.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a physical address associated with a client.
 * The address contains basic location and postal information such as
 * country, city, street, ZIP code, and building number.
 */
@AllArgsConstructor
@Getter
public class Address {
    private String country;
    private String city;
    private String street;
    private String zip;
    private int number;
}
