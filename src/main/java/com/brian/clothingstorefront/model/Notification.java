package com.brian.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Functions as a connector between a User and a given Product Category.
 * Provides the base functionality needed for a user to keep track of
 * updates within a given category
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "category_id"})})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(allocationSize = 100, name = "notification_seq", sequenceName = "notification_seq")
    private long id;
    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User notifyBy;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProdCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id && enabled == that.enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enabled);
    }
}
