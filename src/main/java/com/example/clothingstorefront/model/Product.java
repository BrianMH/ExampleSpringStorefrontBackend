package com.example.clothingstorefront.model;

import com.example.clothingstorefront.model.utility.URLArrayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the root of a product (that is, a product ignoring all variant specifics).
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(allocationSize = 100, name = "product_seq", sequenceName = "product_seq")
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false) // in this case, having no description would make it difficult for a user to use the site
    private String description;
    @Column(name = "OOS", nullable = false)
    private boolean outOfStock;

    // holds our collection of images
    @Convert(converter = URLArrayConverter.class)
    @Column(name = "carousel_arr")
    private List<String> imageCollection;

    // product categories can possibly overlap (a product owns its tags)
    @ManyToMany
    private Set<ProdCategory> prodTags;

    // holds some simple metadata
    @Column(name = "created", nullable = false)
    private Instant dateCreated;

    /**
     * Manages our timestamps for creation.
     */
    @PrePersist
    protected void onCreate() {
        this.dateCreated = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && outOfStock == product.outOfStock && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(dateCreated, product.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, outOfStock, dateCreated);
    }
}
