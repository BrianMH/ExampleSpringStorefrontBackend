package com.brian.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A cart that has been saved by the user. In theory there's no need to save anything other than the collection of items
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cart")
public class SavedCart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String cartName;
    @Column(name = "created", nullable = false)
    private Instant createdOn;

    // connects to user who saved the cart
    @ManyToOne
    @JoinColumn(name = "saved_by", nullable = false)
    private User savedBy;

    // connects to cart elements
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    /**
     * Manages our timestamps for creation.
     */
    @PrePersist
    protected void onCreate() {
        this.createdOn = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedCart savedCart = (SavedCart) o;
        return Objects.equals(id, savedCart.id) && Objects.equals(cartName, savedCart.cartName) && Objects.equals(createdOn, savedCart.createdOn) && Objects.equals(cartItems, savedCart.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartName, createdOn);
    }
}
