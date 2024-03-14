package com.example.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents an internally saved address associated with a given user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(allocationSize = 100, name = "address_seq", sequenceName = "address_seq")
    private long id;
    @Column(nullable = false)
    private String fullName;
    @Column(name = "address1", nullable = false)
    private String fAddress;
    @Column(name = "address2")
    private String sAddress;
    @Column(nullable = false)
    private String city;
    @Column(name = "state_code", length = 2, nullable = false)
    private String state;
    @Column(name = "zip_code", length = 5, nullable = false)
    private String zip;

    // covers the owner of the address
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // This would be used to cover potentially useful information regarding an address
    @Column(name = "extra_info")
    private String extraInfo;

    // helper methods
    @Override
    public String toString() {
        return String.format("{Address [id=%d, of=%s] - (%s, %s, %s, %s, %s)", id,
                fullName, fAddress, sAddress, city, state, zip);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(fullName, address.fullName) &&
                Objects.equals(fAddress, address.fAddress) &&
                Objects.equals(sAddress, address.sAddress) &&
                Objects.equals(city, address.city) &&
                Objects.equals(state, address.state) &&
                Objects.equals(zip, address.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, fAddress, sAddress, city, state, zip);
    }
}
