package com.juancarsg.reviews.backend.util.mapper;

import com.juancarsg.reviews.backend.dto.schedule.ScheduleRequestDto;
import com.juancarsg.reviews.backend.dto.schedule.ScheduleResponseDto;
import com.juancarsg.reviews.backend.entity.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class ScheduleMapper {

    public List<ScheduleResponseDto> convertEntityListToDtoList(List<Schedule> entityList) {
        return entityList.stream().map(this::convertEntityToDto).toList();
    }

    public ScheduleResponseDto convertEntityToDto(Schedule entity) {
        return new ScheduleResponseDto
                (
                        entity.getId(),
                        entity.getDayOfWeek(),
                        entity.getOpeningTime(),
                        entity.getClosingTime()
                );
    }

    public Schedule convertDtoToEntity(ScheduleRequestDto dto) {
        return new Schedule
                (
                        dto.getDayOfWeek(),
                        LocalTime.of(dto.getOpeningHour(), dto.getOpeningMinute()),
                        LocalTime.of(dto.getClosingHour(), dto.getClosingMinute())
                );
    }

}
