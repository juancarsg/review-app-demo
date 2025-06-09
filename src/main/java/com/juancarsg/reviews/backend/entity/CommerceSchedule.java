package com.juancarsg.reviews.backend.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "Commerce_Schedule")
public class CommerceSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public CommerceSchedule(Commerce commerce, Schedule schedule) {
        this.commerce = commerce;
        this.schedule = schedule;
    }

}