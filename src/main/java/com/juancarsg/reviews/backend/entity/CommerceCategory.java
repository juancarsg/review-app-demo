package com.juancarsg.reviews.backend.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "Commerce_Category")
public class CommerceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
