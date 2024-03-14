package com.example.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a potential tag that a product can fall under.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_category")
public class ProdCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_seq")
    @SequenceGenerator(allocationSize = 100, name = "product_category_seq", sequenceName = "product_category_seq")
    private long id;

    @Column(name = "name", nullable = false)
    private String tagName;
    private String description;

    @Column(name = "prod_img", nullable = false)
    private String imageRef;

    // connects all our relevant products to a given category
    @ManyToMany(mappedBy = "prodTags")
    private Set<Product> relProducts;

    @Override
    public String toString() {
        return "{category-" + tagName + " (desc=" + description + ")+(ref=" + imageRef + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdCategory that = (ProdCategory) o;
        return Objects.equals(tagName, that.tagName) && Objects.equals(description, that.description) && Objects.equals(imageRef, that.imageRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, description, imageRef);
    }
}
