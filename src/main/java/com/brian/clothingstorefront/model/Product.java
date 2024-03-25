package com.brian.clothingstorefront.model;

import com.brian.clothingstorefront.model.utility.URLArrayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

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
    @Column(name="price", nullable = false)
    private long priceInCents;

    // holds our collection of images
    @Convert(converter = URLArrayConverter.class)
    @Column(name = "carousel_arr")
    private List<String> imageCollection;

    // for simplicity, enforce a product only is only in one category
    // TODO: This should ideally be many-to-many...
    @ManyToOne(optional = false)
    private ProdCategory prodCategory;

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
    public String toString() {
        return "{Product [id=" + id + "] -> (name=" + name + ")+(description=" + description.substring(0, Math.min(description.length(), 9))
                + ")+(outOfStock=" + outOfStock + ")+(priceInCents=" + priceInCents + ")+(numImages=" + imageCollection.size() + ")+(category="
                + prodCategory + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && outOfStock == product.outOfStock &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(dateCreated, product.dateCreated) &&
                Objects.equals(priceInCents, product.priceInCents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, outOfStock, priceInCents, dateCreated);
    }
}
