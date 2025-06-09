package com.juancarsg.reviews.backend.util.mapper;

import com.juancarsg.reviews.backend.dto.PaginatedDto;
import com.juancarsg.reviews.backend.dto.SimpleDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceCriteriaDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceRequestDto;
import com.juancarsg.reviews.backend.dto.commerce.CommerceResponseDto;
import com.juancarsg.reviews.backend.dto.schedule.ScheduleResponseDto;
import com.juancarsg.reviews.backend.entity.Commerce;
import com.juancarsg.reviews.backend.entity.CommerceCategory;
import com.juancarsg.reviews.backend.entity.CommerceSchedule;
import com.juancarsg.reviews.backend.repository.CategoryRepository;
import com.juancarsg.reviews.backend.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommerceMapper {

    private final CategoryRepository categoryRepository;
    private final ScheduleRepository scheduleRepository;

    public CommerceMapper(CategoryRepository categoryRepository, ScheduleRepository scheduleRepository) {
        this.categoryRepository = categoryRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public PaginatedDto<CommerceResponseDto> convertEntityPageToPaginatedDto(Page<Commerce> entityPage,
                                                                             CommerceCriteriaDto criteria) {
        return new PaginatedDto<>(
                convertEntityListToDtoList(entityPage.getContent(), criteria),
                entityPage.getSize(),
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements()
        );
    }

    public List<CommerceResponseDto> convertEntityListToDtoList(List<Commerce> entityList,
                                                                CommerceCriteriaDto criteria) {
        return entityList.stream().map(entity -> this.convertEntityToDto(entity, criteria)).toList();
    }

    public CommerceResponseDto convertEntityToDto(Commerce entity, CommerceCriteriaDto criteria) {
        CommerceResponseDto dto = new CommerceResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getDescription(),
                entity.getCommerceStat().getAvgRating(),
                entity.getCommerceStat().getCountReviews()
        );
        if (criteria.isIncludeCategories()) {
            dto.setCategories(
                    entity.getCommerceCategories().stream()
                            .map(commerceCategory -> new SimpleDto(
                                    commerceCategory.getCategory().getId(),
                                    commerceCategory.getCategory().getName()))
                            .sorted(Comparator.comparing(SimpleDto::getId)).collect(Collectors.toSet())
            );
        }
        if (criteria.isIncludeSchedules()) {
            dto.setSchedules(entity.getCommerceSchedules().stream()
                    .map(schedule -> new ScheduleResponseDto(
                            schedule.getSchedule().getId(),
                            schedule.getSchedule().getDayOfWeek(),
                            schedule.getSchedule().getOpeningTime(),
                            schedule.getSchedule().getOpeningTime()))
                    .sorted(Comparator.comparing(ScheduleResponseDto::getId)).collect(Collectors.toSet()));
        }
        return dto;
    }

    public Commerce updateEntityWithDto(Commerce entity, CommerceRequestDto dto) {
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            entity.setName(dto.getName());
        }
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getCategories() != null && !dto.getCategories().isEmpty()) {
            Commerce finalCommerce = entity;
            Set<CommerceCategory> currentCommerceCategories = finalCommerce.getCommerceCategories();
            Set<CommerceCategory> updatedCommerceCategories = dto.getCategories().stream().map(categoryId -> {
                CommerceCategory commerceCategory = new CommerceCategory();
                commerceCategory.setCommerce(finalCommerce);
                commerceCategory.setCategory(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Category with id " + categoryId + " not found")));
                return commerceCategory;
            }).collect(Collectors.toSet());
            currentCommerceCategories.removeIf(commerceCategory -> !updatedCommerceCategories.contains(commerceCategory));
            currentCommerceCategories.addAll(updatedCommerceCategories);
            entity.setCommerceCategories(currentCommerceCategories);
        }
        if (dto.getSchedules() != null && !dto.getCategories().isEmpty()) {
            Commerce finalCommerce = entity;
            Set<CommerceSchedule> currentCommerceSchedule = finalCommerce.getCommerceSchedules();
            Set<CommerceSchedule> updatedCommerceSchedule = dto.getSchedules().stream().map(scheduleId ->{
                CommerceSchedule commerceSchedule = new CommerceSchedule();
                commerceSchedule.setCommerce(finalCommerce);
                commerceSchedule.setSchedule(scheduleRepository.findById(scheduleId)
                        .orElseThrow(() -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")));
                return commerceSchedule;
            }).collect(Collectors.toSet());
            currentCommerceSchedule.removeIf(commerceSchedule -> !updatedCommerceSchedule.contains(commerceSchedule));
            currentCommerceSchedule.addAll(updatedCommerceSchedule);
            entity.setCommerceSchedules(currentCommerceSchedule);
        }
        return entity;
    }

    public Commerce convertDtoToEntity(CommerceRequestDto dto) {
        Commerce entity = new Commerce(dto.getName(), dto.getAddress(), dto.getPhone(), dto.getDescription());
        Set<CommerceCategory> commerceCategories = new HashSet<>();
        dto.getCategories().forEach(categoryId -> {
            CommerceCategory commerceCategory = new CommerceCategory();
            commerceCategory.setCommerce(entity);
            commerceCategory.setCategory(categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category with id " + categoryId + " not found")));
            commerceCategories.add(commerceCategory);
        });
        entity.setCommerceCategories(commerceCategories);
        Set<CommerceSchedule> commerceSchedules = new HashSet<>();
        dto.getSchedules().forEach(scheduleId -> {
            CommerceSchedule commerceSchedule = new CommerceSchedule();
            commerceSchedule.setCommerce(entity);
            commerceSchedule.setSchedule(scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")));
            commerceSchedules.add(commerceSchedule);
        });
        entity.setCommerceSchedules(commerceSchedules);
        return entity;
    }

}
