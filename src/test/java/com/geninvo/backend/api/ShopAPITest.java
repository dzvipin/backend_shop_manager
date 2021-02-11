package com.geninvo.backend.api;

import com.geninvo.backend.builder.AddressBuilder;
import com.geninvo.backend.builder.ShopBuilder;
import com.geninvo.backend.builder.ShopCreateDtoBuilder;
import com.geninvo.backend.builder.dto.ListDto;
import com.geninvo.backend.modal.dto.SearchDto;
import com.geninvo.backend.modal.dto.ShopCreateDto;
import com.geninvo.backend.modal.dto.ShopDto;
import com.geninvo.backend.modal.entity.Address;
import com.geninvo.backend.modal.entity.Shop;
import com.geninvo.backend.repository.AddressRepository;
import com.geninvo.backend.repository.ShopRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

public class ShopAPITest extends BaseAPIIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    public void create() {

        ShopCreateDto shopCreateDto = ShopCreateDtoBuilder
                .sample()
                .build();

        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity entity = new HttpEntity(shopCreateDto, headers);

        ResponseEntity<ShopDto> response = restTemplate.exchange("/v1/api/shop", POST, entity, ShopDto.class);
        assertEquals(OK, response.getStatusCode());
        assertTrue(response
                .getBody()
                .getId() > 0);
        assertEquals(
                shopCreateDto.getName(),
                response
                        .getBody()
                        .getName());
        assertEquals(
                shopCreateDto.getOwnerName(),
                response
                        .getBody()
                        .getOwnerName());

    }

    @Test
    public void getAll() {
        Shop shop = createShop(6.55526689, 73.13373892);

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ListDto<ShopDto>> response;
        response = restTemplate.exchange("/v1/api/shop", GET, entity, new ParameterizedTypeReference<ListDto<ShopDto>>() {
        });
        assertEquals(OK, response.getStatusCode());
        assertTrue(!response
                .getBody()
                .isEmpty());
    }

    @Test
    public void searchUsingName() {

        Shop shop = createShop(3.48835279, -76.51532198);

        SearchDto searchDto = new SearchDto();
        searchDto.setName(shop
                .getName()
                .substring(1, 6));

        HttpEntity entity = new HttpEntity(searchDto, headers);
        ResponseEntity<ListDto<ShopDto>> response;
        response = restTemplate.exchange("/v1/api/shop/search", POST, entity, new ParameterizedTypeReference<ListDto<ShopDto>>() {
        });
        assertEquals(OK, response.getStatusCode());
        assertTrue(!response
                .getBody()
                .isEmpty());
        assertTrue(response
                .getBody()
                .getTotalElements() == 1);
        assertEquals(
                shop.getName(),
                response
                        .getBody()
                        .getContent()
                        .get(0).name);
    }

    @Test
    public void searchUsingCoordinates() {

        Shop shopA = createShop(7.06125013, -73.8492855);
        Shop shopB = createShop(7.88475514, -72.49432589);
        Shop shopC = createShop(4.1351088, -73.63690401);

        SearchDto searchDto = new SearchDto();
        searchDto.setLatitude(7.08594109039762);
        searchDto.setLongitude(286.95225338731285);

        HttpEntity entity = new HttpEntity(searchDto, headers);
        ResponseEntity<ListDto<ShopDto>> response;
        response = restTemplate.exchange("/v1/api/shop/search", POST, entity, new ParameterizedTypeReference<ListDto<ShopDto>>() {
        });
        assertEquals(OK, response.getStatusCode());
        assertTrue(!response
                .getBody()
                .isEmpty());
        assertTrue(response
                .getBody()
                .getTotalElements() == 2);
    }

    @Test
    public void searchWithoutNameAndCoordinates() {

        Shop shopA = createShop(7.06125013, -73.8492855);

        HttpEntity entity = new HttpEntity(new SearchDto(), headers);
        ResponseEntity<ListDto<ShopDto>> response;
        response = restTemplate.exchange("/v1/api/shop/search", POST, entity, new ParameterizedTypeReference<ListDto<ShopDto>>() {
        });
        assertEquals(OK, response.getStatusCode());
        assertTrue(!response
                .getBody()
                .isEmpty());
        assertTrue(response
                .getBody()
                .getTotalElements() >= 1);
    }

    private Shop createShop(Double lat, Double lng) {
        Address address = AddressBuilder
                .sample()
                .withLat(lat)
                .withLongitude(lng)
                .build();
        address = addressRepository.save(address);
        Shop shop = ShopBuilder
                .sample()
                .withCategory(Shop.Category.MALL)
                .build();
        shop.setAddress(address);
        return shopRepository.save(shop);
    }

}
