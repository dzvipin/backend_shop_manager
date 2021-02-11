package com.geninvo.backend.modal.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Address dto used to save coordinates of a location")
public class AddressDto implements Serializable {

    @ApiModelProperty(notes = "Generated user ID")
    public Long id;

    @ApiModelProperty(notes = "Name Of Location")
    public String locationName;

    @ApiModelProperty(notes = "Latitude of location")
    public Double latitude;

    @ApiModelProperty(notes = "Longitude of location")
    public Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
