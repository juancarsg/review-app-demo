package com.juancarsg.reviews.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user", "commerce"})
@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column
    private int rating;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDate.from(LocalDateTime.now());
    }

    public Review(String content, int rating, User user, Commerce commerce) {
        this.content = content;
        this.rating = rating;
        this.user = user;
        this.commerce = commerce;
    }
}
