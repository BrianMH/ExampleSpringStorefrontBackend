package com.example.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Facilitates in transferring of user objects
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("userId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID userId;

    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("screenName")
    private String screenName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("avatarRef")
    private String avatarRef; // TODO: Implement some proper uploading methods for avatarRef

    // Authorization is determined by this value
    @JsonProperty("role")
    private String role;

    // Additional resources would be present within addressDTO
    @JsonProperty("addressInfo")
    AddressDTO mainAddress;
}
