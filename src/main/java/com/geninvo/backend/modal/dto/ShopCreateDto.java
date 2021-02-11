package com.geninvo.backend.modal.dto;

import com.geninvo.backend.modal.entity.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Shop Create Dto used to create new shop")
public class ShopCreateDto {

    @ApiModelProperty(notes = "Shop name")
    private String name;

    @ApiModelProperty("Shop Category")
    private Shop.Category category;

    @ApiModelProperty(notes = "Shop owner name")
    private String ownerName;

    @ApiModelProperty(notes = "Name of locatopn")
    private String locationName;

    @ApiModelProperty(notes = "Latitude of location")
    private Double latitude;

    @ApiModelProperty(notes = "Longitude of location")
    private Double longitude;

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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

}
