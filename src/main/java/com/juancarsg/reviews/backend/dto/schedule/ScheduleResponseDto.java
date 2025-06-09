package com.juancarsg.reviews.backend.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScheduleResponseDto {
    private Long id;
    private String dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
