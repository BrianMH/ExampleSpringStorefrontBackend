package com.example.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an individual associated with the application that has some information saved due to account creation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    /** User attributes **/
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    // TODO: Strengthen password field
    @Column(nullable = false)
    private String password;

    // TODO: Add role functionality to user
    // private Role userRole;

    @Column(name = "avatar_ref", nullable = false)
    private String avatarRef;

    /** Connections with other entities **/
    // Connects user to their addresses
    @OneToMany(mappedBy = "createdBy")
    private Set<Address> addressBook;

    // connects uer to their carts
    @OneToMany(mappedBy = "savedBy")
    private Set<SavedCart> savedCarts;

    // connects users with account details
    @OneToOne(mappedBy = "user")
    private UserDetails userDetails;

    // connects users to their notifications
    @OneToMany(mappedBy = "notifyBy")
    private Set<Notification> userNotifs;

    /** Metadata **/
    @Column(name = "created", nullable = false)
    private Instant createdOn;

    @Column(name = "updated", nullable = false)
    private Instant updatedOn;

    /**
     * Manages our timestamps for creation and also sets the user avatar to the default.
     */
    @PrePersist
    protected void onCreate() {
        // change times
        Instant nowTime = Instant.now();
        this.createdOn = nowTime;
        this.updatedOn = nowTime;

        // set default avatar
        this.avatarRef = "";
    }

    @Override
    public String toString() {
        return "{User (UUID=" + userId + "): email-" + email + ",username=" + username + ",avatarRef=" + avatarRef +
                ",userDetails=" + userDetails + ",createdOn=" + createdOn + ",updatedOn" + updatedOn + "}";
    }
}
