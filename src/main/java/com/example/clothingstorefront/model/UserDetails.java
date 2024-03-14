package com.example.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Stores any non-account critical information that would be associated with a user. This includes information such as
 * the account mailing address, an associated account name, and a date of birth that can be used as collected metadata
 * in the future.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class UserDetails {
    @Id
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;

    // this object stores both the user's name and their mailing address
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address mailAddress;

    // and this stores a user's date of birth
    @Column(name = "birth_date")
    private LocalDate dateOfBirth;

    @Override
    public String toString() {
        return "{UserDetails (UUID=" + userId + ") address-" + mailAddress + ",birthDate" + dateOfBirth + "}";
    }
}
