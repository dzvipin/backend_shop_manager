package com.geninvo.backend.builder;

import com.geninvo.backend.modal.entity.Address;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class AddressBuilder {

    public Long id;
    public String locationName = randomAlphabetic(10);
    public Double latitude = 4.66455174;
    public Double longitude = -74.07867091;

    public static AddressBuilder sample() {
        return new AddressBuilder();
    }

    public Address build() {
        Address address = new Address();
        address.setId(id);
        address.setLocationName(locationName);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        return address;
    }

    public AddressBuilder withLat(Double lat) {
        this.latitude = lat;
        return this;
    }

    public AddressBuilder withLongitude(Double lng) {
        this.longitude = lng;
        return this;
    }

}
