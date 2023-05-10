package com.example.shop_pet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String formattedAddress;
    private String city;
    private String district;
    private String state;
    private String country;
    private String postalCode;

    public Address(String formattedAddress, String city, String district, String state, String country,
            String postalCode) {
        this.formattedAddress = formattedAddress;
        this.city = city;
        this.district = district;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }
}
