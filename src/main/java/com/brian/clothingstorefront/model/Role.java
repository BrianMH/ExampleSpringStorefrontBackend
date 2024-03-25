package com.brian.clothingstorefront.model;

/**
 * Defines the roles available to our system right now. In theory, this could be swapped to a lookup table, but there
 * was no need to complicate things too much for this application.
 */
public enum Role {
    ROLE_ANON,
    ROLE_USER,
    ROLE_ADMIN;
}
