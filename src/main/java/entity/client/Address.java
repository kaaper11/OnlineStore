package entity.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Address {
    private String country;
    private String city;
    private String street;
    private String zip;
    private int number;
}
