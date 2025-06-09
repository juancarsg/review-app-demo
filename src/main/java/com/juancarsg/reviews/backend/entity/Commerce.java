package com.juancarsg.reviews.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"commerceCategories", "commerceSchedules", "reviews", "commerceStat"})
@Entity
@Table(name = "Commerce")
public class Commerce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CommerceCategory> commerceCategories;

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CommerceSchedule> commerceSchedules;

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Review> reviews;

    @OneToOne(mappedBy = "commerce", cascade = CascadeType.ALL, orphanRemoval = true)
    private CommerceStat commerceStat;

    @PostPersist
    private void createCommerceStat() {
        if (this.commerceStat == null) {
            CommerceStat newCommerceStat = new CommerceStat();
            newCommerceStat.setCommerce(this);
            newCommerceStat.setAvgRating(0.0);
            newCommerceStat.setCountReviews(0L);
            newCommerceStat.setLastUpdated(LocalDateTime.now());
            this.commerceStat = newCommerceStat;
        }
    }

    public Commerce(String name, String address, String phone, String description) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
}
