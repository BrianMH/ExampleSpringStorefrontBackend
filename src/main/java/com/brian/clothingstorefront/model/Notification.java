package com.brian.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Functions as a connector between a User and a given Product Category.
 * Provides the base functionality needed for a user to keep track of
 * updates within a given category
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
