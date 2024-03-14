package com.example.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a particular representation of a product. For the store in my mind, only sizes change the price of a
 * product, but it may be possible that more factors are used to distinguish between variants, in which case the proper
 * way to represent a variant may through either an attribute table or embedded class.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_variant", uniqueConstraints = {@UniqueConstraint(columnNames = {"prod_id", "size", "color"})})
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_seq")
    @SequenceGenerator(allocationSize = 100, name = "product_variant_seq", sequenceName = "product_variant_seq")
    private long id;

    // our connection to our main product
    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product mainProd;

    // our only distinguishing factor in this store would be size
    @Column(nullable = false)
    private String size;

    // but color might be useful down the line as well; so, we can keep it stored
    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariant that = (ProductVariant) o;
        return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(mainProd, that.mainProd) && Objects.equals(size, that.size) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainProd, size, color, price);
    }
}
