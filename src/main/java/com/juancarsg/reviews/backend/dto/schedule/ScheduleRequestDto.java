package com.juancarsg.reviews.backend.dto.schedule;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ScheduleRequestDto {
    @NotEmpty
    @Pattern(regexp = "^(MON|TUE|WED|THU|FRI|SAT|SUN)$",
             message = "The value for dayOfWeek must be one of the following: MON, TUE, WED, THU, FRI, SAT, SUN")
    private String dayOfWeek;
    @NotNull
    @Min(0)
    @Max(23)
    private Integer openingHour;
    @NotNull
    @Min(0)
    @Max(59)
    private Integer openingMinute;
    @NotNull
    @Min(0)
    @Max(23)
    private Integer closingHour;
    @NotNull
    @Min(0)
    @Max(59)
    private Integer closingMinute;
}
