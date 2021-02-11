package com.geninvo.backend.service;

import java.util.Objects;
import com.geninvo.backend.modal.dto.SearchDto;
import com.geninvo.backend.modal.entity.Address;
import com.geninvo.backend.modal.entity.Shop;
import com.geninvo.backend.repository.AddressRepository;
import com.geninvo.backend.repository.ShopRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    @Value("${app.nearest.location.radius}")
    private Integer distance;

    private ShopRepository shopRepository;
    private AddressRepository addressRepository;

    @Autowired
    public ShopService(final ShopRepository shopRepository, final AddressRepository addressRepository) {
        this.shopRepository = shopRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Method used to create new Shop
     *
     * @param entity : Shop
     *
     * @return : Shop
     */
    @Transactional
    public Shop create(Shop entity) {
        assert entity != null;
        validate(entity);
        Address address = addressRepository.save(entity.getAddress());
        entity.setAddress(address);
        LOGGER.info("Creating Shop with NAME ({}), CATEGORY ({}), OWNER ({})",
                entity.getName(), entity.getCategory(), entity.getOwnerName());
        return shopRepository.save(entity);
    }

    /**
     * Method used to get all shops in pageable form
     *
     * @param pageable :  pageable
     *
     * @return :  List of shops
     */
    @Transactional(readOnly = true)
    public Page<Shop> getAll(Pageable pageable) {
        return shopRepository.findAll(pageable);
    }

    /**
     * Method used to search shops according to shop name and coordinates
     *
     * @param pageable :  Pageable
     *
     * @return :  List of shops
     */
    @Transactional
    public Page<Shop> search(SearchDto dto, Pageable pageable) {
        if (StringUtils.isNotBlank(dto.getName())) {
            return shopRepository.findShopsByNameAndLocation(dto.getName(), null, null, distance, pageable);
        } else if (Objects.nonNull(dto.getLatitude()) && Objects.nonNull(dto.getLongitude())) {
            return shopRepository.findShopsByNameAndLocation(null, dto.getLatitude(), dto.getLongitude(), distance, pageable);
        } else {
            return getAll(pageable);
        }
    }

    /**
     * Private method to validate fields at time of creating a new shop/
     *
     * @param shop :  Shop
     */
    private void validate(Shop shop) {
        if (StringUtils.isBlank(shop.getName())) {
            throw new IllegalArgumentException("Shop name must not be null/empty");
        }

        if (StringUtils.isBlank(shop.getOwnerName())) {
            throw new IllegalArgumentException("Shop owner name must not be null/empty");
        }

        if (Objects.isNull(shop.getCategory())) {
            throw new IllegalArgumentException("Category must not be null/empty");
        }

        if (Objects.isNull(shop.getAddress())) {
            throw new IllegalArgumentException("Address must not be null");

        }

        if (Objects.isNull(shop
                .getAddress()
                .getLatitude())) {
            throw new IllegalArgumentException("Latitude must not be null");
        }

        if (Objects.isNull(shop
                .getAddress()
                .getLongitude())) {
            throw new IllegalArgumentException("Longitude must not be null");
        }
    }

}
