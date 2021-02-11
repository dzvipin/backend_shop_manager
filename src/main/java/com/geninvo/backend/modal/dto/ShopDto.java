package com.geninvo.backend.modal.dto;

import java.io.Serializable;
import com.geninvo.backend.modal.entity.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ShopDto implements Serializable {

    @ApiModelProperty(notes = "Generated user ID")
    public Long id;

    @ApiModelProperty(notes = "Shop name")
    public String name;

    @ApiModelProperty("Shop Category")
    public Shop.Category category;

    @ApiModelProperty(notes = "Shop owner name")
    public String ownerName;

    @ApiModelProperty(notes = "Shop address")
    public AddressDto address;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Shop.Category getCategory() {
        return category;
    }

    public void setCategory(final Shop.Category category) {
        this.category = category;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(final AddressDto address) {
        this.address = address;
    }

}
