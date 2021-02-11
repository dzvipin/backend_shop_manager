package com.geninvo.backend.builder;

import com.geninvo.backend.modal.dto.ShopCreateDto;
import com.geninvo.backend.modal.entity.Shop;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class ShopCreateDtoBuilder {

    public String name = randomAlphabetic(10);
    public Shop.Category category = Shop.Category.GENERALSTORE;
    public String ownerName = randomAlphabetic(10);
    public String locationName = randomAlphabetic(10);
    public Double latitude = 4.66455174;
    public Double longitude = -74.07867091;

    public static ShopCreateDtoBuilder sample() {
        return new ShopCreateDtoBuilder();
    }

    public ShopCreateDto build() {
        ShopCreateDto shopCreateDto = new ShopCreateDto();
        shopCreateDto.setCategory(category);
        shopCreateDto.setName(name);
        shopCreateDto.setOwnerName(ownerName);
        shopCreateDto.setLocationName(locationName);
        shopCreateDto.setLatitude(latitude);
        shopCreateDto.setLongitude(longitude);
        return shopCreateDto;
    }

    public ShopCreateDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ShopCreateDtoBuilder withOwnerName(String name) {
        this.ownerName = name;
        return this;
    }

    public ShopCreateDtoBuilder withCategory(Shop.Category category) {
        this.category = category;
        return this;
    }

    public ShopCreateDtoBuilder withLat(Double lat) {
        this.latitude = lat;
        return this;
    }

    public ShopCreateDtoBuilder withLongitude(Double lng) {
        this.longitude = lng;
        return this;
    }
}
