package com.juancarsg.reviews.backend.controller;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceCriteriaDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceRequestDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceResponseDto;
import com.juancarsg.reviews.backend.service.CommerceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.juancarsg.reviews.backend.controller.CommerceController.API_PATH_COMMERCES;

@RestController
@RequestMapping(API_PATH_COMMERCES)
public class CommerceController {

    public static final String API_PATH_COMMERCES = "/api/v1/commerces";

    private final CommerceService commerceService;

    public CommerceController(CommerceService commerceService) {
        this.commerceService = commerceService;
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<CommerceResponseDto>> getCommerces(@ModelAttribute CommerceCriteriaDto criteria) {
        return commerceService.getCommerces(criteria);
    }

    @PostMapping
    public ResponseEntity<CommerceResponseDto> createCommerce(@Validated(CommerceRequestDto.ValidationGroups.Create.class)
                                                                  @RequestBody CommerceRequestDto createCommerceDto) {
        return commerceService.createCommerce(createCommerceDto);
    }

    @PatchMapping
    public ResponseEntity<CommerceResponseDto> updateCommerce(@RequestParam Long id,
                                                              @Validated(CommerceRequestDto.ValidationGroups.Update.class)
                                                                  @RequestBody CommerceRequestDto updatedCommerceDto) {
        return commerceService.updateCommerce(id, updatedCommerceDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCommerce(@RequestParam Long commerceId) {
        return commerceService.deleteCommerceById(commerceId);
    }

}
