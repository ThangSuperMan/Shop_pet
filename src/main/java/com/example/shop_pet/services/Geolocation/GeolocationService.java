package com.example.shop_pet.services.Geolocation;

import com.example.shop_pet.models.Address;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeolocationService {

    @Value("${google.api.key}")
    private String apiKey;

    public Address getAddress(double latitude, double longitude) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        LatLng location = new LatLng(latitude, longitude);
        GeocodingResult[] results = GeocodingApi.newRequest(context)
                .latlng(location)
                .resultType(AddressType.STREET_ADDRESS)
                .await();

        if (results.length == 0) {
            throw new Exception("No results found");
        }

        Address address = new Address();
        address.setFormattedAddress(results[0].formattedAddress);
        address.setCity(results[0].addressComponents[0].longName);
        address.setDistrict(results[0].addressComponents[1].longName);
        address.setState(results[0].addressComponents[2].longName);
        address.setCountry(results[0].addressComponents[3].longName);
        address.setPostalCode(results[0].addressComponents[4].longName);

        return address;
    }
}