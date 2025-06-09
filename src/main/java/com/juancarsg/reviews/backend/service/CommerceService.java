package com.juancarsg.reviews.backend.service;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceCriteriaDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceRequestDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommerceService {
    ResponseEntity<PaginatedDto<CommerceResponseDto>> getCommerces(CommerceCriteriaDto criteria);
    ResponseEntity<CommerceResponseDto> createCommerce(CommerceRequestDto creationDto);
    ResponseEntity<CommerceResponseDto> updateCommerce(Long id, CommerceRequestDto updateDto);
    ResponseEntity<Void> deleteCommerceById(Long id);
}


