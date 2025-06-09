package com.juancarsg.reviews.backend.entity;

import com.juancarsg.reviews.backend.exception.CustomException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "commerceSchedules")
@Entity
@Table(name = "Schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommerceSchedule> commerceSchedules;

    @PreRemove
    private void checkCommerceScheduleBeforeDelete() {
        if (commerceSchedules != null && !commerceSchedules.isEmpty()) {
            throw new CustomException("There are commerces associated to this schedule.", HttpStatus.BAD_REQUEST.value());
        }
    }

    public Schedule(String dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

}