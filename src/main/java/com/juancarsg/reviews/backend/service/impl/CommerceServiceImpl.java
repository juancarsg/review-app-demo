package com.juancarsg.reviews.backend.service.impl;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceCriteriaDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceRequestDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceResponseDto;
import com.juancarsg.reviews.backend.entity.Commerce;
import com.juancarsg.reviews.backend.repository.CommerceRepository;
import com.juancarsg.reviews.backend.repository.ReviewRepository;
import com.juancarsg.reviews.backend.service.CommerceService;
import com.juancarsg.reviews.backend.specification.CommerceSpecification;
import com.juancarsg.reviews.backend.util.mapper.CommerceMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommerceServiceImpl implements CommerceService {

    private final CommerceRepository commerceRepository;
    private final ReviewRepository reviewRepository;
    private final CommerceMapper commerceMapper;

    public CommerceServiceImpl(CommerceRepository commerceRepository, ReviewRepository reviewRepository, CommerceMapper commerceMapper) {
        this.commerceRepository = commerceRepository;
        this.reviewRepository = reviewRepository;
        this.commerceMapper = commerceMapper;
    }

    @Transactional
    public ResponseEntity<PaginatedDto<CommerceResponseDto>> getCommerces(CommerceCriteriaDto criteria) {
        Specification<Commerce> specification = CommerceSpecification.getCommerceSpecification(criteria);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize());
        Page<Commerce> commerces = commerceRepository.findAll(specification, pageable);
        return commerces.getContent().isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(commerceMapper.convertEntityPageToPaginatedDto(commerces, criteria));
    }

    public ResponseEntity<CommerceResponseDto> createCommerce(CommerceRequestDto creationDto) {
        Commerce commerce = commerceRepository.save(commerceMapper.convertDtoToEntity(creationDto));
        CommerceCriteriaDto criteria = new CommerceCriteriaDto();
        criteria.setIncludeCategories(true);
        criteria.setIncludeSchedules(true);
        return ResponseEntity.ok(commerceMapper.convertEntityToDto(commerce, criteria));
    }

    @Transactional
    public ResponseEntity<CommerceResponseDto> updateCommerce(Long id, CommerceRequestDto updateDto) {
        Commerce commerce = commerceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commerce with id " + id + " not found."));
        commerce = commerceMapper.updateEntityWithDto(commerce, updateDto);
        commerce = commerceRepository.save(commerce);
        CommerceCriteriaDto criteria = new CommerceCriteriaDto();
        criteria.setIncludeCategories(true);
        criteria.setIncludeSchedules(true);
        return ResponseEntity.ok(commerceMapper.convertEntityToDto(commerce, criteria));
    }

    public ResponseEntity<Void> deleteCommerceById(Long id) {
        //TODO: boost performance applying batch deleting for related reviews
        if (commerceRepository.existsById(id)) {
            reviewRepository.deleteByCommerceId(id);
            commerceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
