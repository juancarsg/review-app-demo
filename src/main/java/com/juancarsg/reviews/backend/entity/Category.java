package com.juancarsg.reviews.backend.entity;

import com.juancarsg.reviews.backend.exception.CustomException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "commerceCategories")
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommerceCategory> commerceCategories;

    @PreRemove
    private void checkCommerceCategoriesBeforeDelete() {
        if (commerceCategories != null && !commerceCategories.isEmpty()) {
            throw new CustomException("There are commerces associated to this category.", HttpStatus.BAD_REQUEST.value());
        }
    }

    public Category(String name) {
        this.name = name;
    }

}
