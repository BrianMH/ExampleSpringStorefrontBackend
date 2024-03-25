package com.brian.clothingstorefront.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Represents the collected user messages. While it could be associated with a user account, for now
 * it will remain simple and exist statically in a state in which only those with dashboard access can view
 * the messages
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inbox_seq")
    @SequenceGenerator(allocationSize = 100, name = "inbox_seq", sequenceName = "inbox_seq")
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String messageContent;
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
        return "{Message: [id=" + id + "] - (" + name + "-" + email + ")";
    }
}
