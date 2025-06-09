package com.juancarsg.reviews.backend.service.impl;

import com.juancarsg.reviews.backend.dto.schedule.ScheduleRequestDto;
import com.juancarsg.reviews.backend.dto.schedule.ScheduleResponseDto;
import com.juancarsg.reviews.backend.entity.Schedule;
import com.juancarsg.reviews.backend.exception.CustomException;
import com.juancarsg.reviews.backend.repository.ScheduleRepository;
import com.juancarsg.reviews.backend.service.ScheduleService;
import com.juancarsg.reviews.backend.util.mapper.ScheduleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(scheduleMapper.convertEntityListToDtoList(schedules));
    }

    public ResponseEntity<ScheduleResponseDto> createSchedule(ScheduleRequestDto request) {
        if (LocalTime.of(request.getOpeningHour(), request.getOpeningMinute())
                .isAfter(LocalTime.of(request.getClosingHour(), request.getClosingMinute()))) {
            throw new CustomException("Opening time must be before closing time.", HttpStatus.BAD_REQUEST.value());
        }
        Schedule schedule = scheduleRepository.save(scheduleMapper.convertDtoToEntity(request));
        return ResponseEntity.ok(scheduleMapper.convertEntityToDto(schedule));
    }

    public ResponseEntity<Void> deleteScheduleById(Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
