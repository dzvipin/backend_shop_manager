package com.geninvo.backend.modal.mapper;

import com.geninvo.backend.modal.dto.ShopCreateDto;
import com.geninvo.backend.modal.dto.ShopDto;
import com.geninvo.backend.modal.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, nullValueMappingStrategy = RETURN_NULL)
public abstract class ShopMapper {


    @Mappings({
              })
    public abstract ShopDto toShopDto(Shop shop);

    @Mappings({
                      @Mapping(source = "locationName", target = "address.locationName"),
                      @Mapping(source = "latitude", target = "address.latitude"),
                      @Mapping(source = "longitude", target = "address.longitude"),
                      @Mapping(target = "address.id", ignore = true),
              })
    public abstract Shop fromShopCreateDto(ShopCreateDto dto);

}
