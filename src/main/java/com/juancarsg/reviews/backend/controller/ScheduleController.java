package com.juancarsg.reviews.backend.controller;

import com.juancarsg.reviews.backend.dto.schedule.ScheduleRequestDto;
import com.juancarsg.reviews.backend.dto.schedule.ScheduleResponseDto;
import com.juancarsg.reviews.backend.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.juancarsg.reviews.backend.controller.ScheduleController.API_PATH_SCHEDULES;

@RestController
@RequestMapping(API_PATH_SCHEDULES)
public class ScheduleController {

    public static final String API_PATH_SCHEDULES = "/api/v1/schedules";

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
        return scheduleService.getSchedules();
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSchedule(@RequestParam Long scheduleId) {
        return scheduleService.deleteScheduleById(scheduleId);
    }

}
