package com.brian.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a single item in a cart. This contains the item's duplicity along with the particular variant of the item
 * that was requested.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"cart_id", "product_id"})})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq")
    @SequenceGenerator(allocationSize = 100, name = "cart_item_seq", sequenceName = "cart_item_seq")
    private long id;

    // Relevant cart info
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private SavedCart cart;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductVariant product;
    @Column(nullable = false)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id && quantity == cartItem.quantity && Objects.equals(cart, cartItem.cart) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
