package com.geninvo.backend.controller;

import javax.validation.Valid;
import com.geninvo.backend.config.PageRequestBuilder;
import com.geninvo.backend.modal.dto.SearchDto;
import com.geninvo.backend.modal.dto.ShopCreateDto;
import com.geninvo.backend.modal.dto.ShopDto;
import com.geninvo.backend.modal.mapper.ShopMapper;
import com.geninvo.backend.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Shops Management")
@RestController
@RequestMapping("/v1/api/shop")
@CrossOrigin
public class ShopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopMapper shopMapper;

    /**
     * Api used to create a new shop
     *
     * @param dto :  shopCreateDto
     *
     * @return :  created shop
     */
    @ApiOperation(value = "Create shop")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = {
            ""
    }, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopDto> create(@RequestBody ShopCreateDto dto) {
        return ResponseEntity
                .ok()
                .body(shopMapper.toShopDto(shopService.create(shopMapper.fromShopCreateDto(dto))));
    }

    /**
     * Api used to get all shops
     *
     * @param pageNumber : PageNumber
     * @param pageSize   : PageSize
     * @param sort       : Sort
     *
     * @return : List of shops
     */
    @ApiOperation(value = "Get All shops")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(value = {
            ""
    }, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ShopDto>> getAll(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sort) {

        PageRequest pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sort);

        return ResponseEntity
                .ok()
                .body(shopService
                        .getAll(pageRequest)
                        .map(shop -> shopMapper.toShopDto(shop)));
    }

    /**
     * Api used to search shop by name and coordinates
     *
     * @param dto        : SearchDto
     * @param pageNumber : PageNumber
     * @param pageSize   : PageSize
     * @param sort       : Sort
     *
     * @return : List of shops
     */
    @ApiOperation(value = "Search shop by name and coordinates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @PostMapping(value = {
            "/search"
    }, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ShopDto>> search(
            @Valid @RequestBody SearchDto dto,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sort) {

        PageRequest pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sort);

        return ResponseEntity
                .ok()
                .body(shopService
                        .search(dto, pageRequest)
                        .map(shop -> shopMapper.toShopDto(shop)));
    }

}
