package com.juancarsg.reviews.backend.dto.commerce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.juancarsg.reviews.backend.dto.SimpleDto;
import com.juancarsg.reviews.backend.dto.schedule.ScheduleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommerceResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private Double rating;
    private Long reviews;
    private Set<SimpleDto> categories;
    private Set<ScheduleResponseDto> schedules;

    public CommerceResponseDto(Long id, String name, String address,
                               String phone, String description,
                               Double rating, Long reviews) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.rating = rating;
        this.reviews = reviews;
    }
}
