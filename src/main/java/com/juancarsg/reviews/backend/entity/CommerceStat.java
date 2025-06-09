package com.juancarsg.reviews.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Commerce_Stat")
public class CommerceStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avg_rating")
    private Double avgRating;

    @Column(name = "count_reviews")
    private Long countReviews;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id", referencedColumnName = "id")
    private Commerce commerce;

    public CommerceStat(Commerce commerce) {
        this.commerce = commerce;
    }

}
