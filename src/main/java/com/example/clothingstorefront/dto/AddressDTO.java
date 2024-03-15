package com.example.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Used to transfer address objects
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @JsonProperty("addressId")  // not used for add
    private long id;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("address1")
    private String fAddress;
    @JsonProperty("address2")
    private String sAddress;
    @JsonProperty("city")
    private String city;
    @JsonProperty("stateCode")
    private String state;
    @JsonProperty("zipCode")
    private String zip;
    @JsonProperty("extraInfo")
    private String extraInfo;

    @JsonProperty("queryUser")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID userId;
}
