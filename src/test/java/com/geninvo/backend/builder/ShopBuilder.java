package com.geninvo.backend.builder;

import com.geninvo.backend.modal.entity.Address;
import com.geninvo.backend.modal.entity.Shop;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class ShopBuilder {

    public Long id;
    public String name = randomAlphabetic(10);
    public Shop.Category category = Shop.Category.GENERALSTORE;
    public String ownerName = randomAlphabetic(10);
    public Address address;

    public static ShopBuilder sample() {
        return new ShopBuilder();
    }

    public Shop build() {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setCategory(category);
        shop.setOwnerName(ownerName);
        shop.setAddress(address);


        return shop;
    }

    public ShopBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ShopBuilder withOwnerName(String name) {
        this.ownerName = name;
        return this;
    }

    public ShopBuilder withCategory(Shop.Category category) {
        this.category = category;
        return this;
    }
}
