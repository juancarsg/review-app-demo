package com.juancarsg.reviews.backend.service;

import com.juancarsg.reviews.backend.dto.schedule.ScheduleRequestDto;
import com.juancarsg.reviews.backend.dto.schedule.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {
    ResponseEntity<List<ScheduleResponseDto>> getSchedules();
    ResponseEntity<ScheduleResponseDto> createSchedule(ScheduleRequestDto request);
    ResponseEntity<Void> deleteScheduleById(Long id);
}


