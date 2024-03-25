package com.brian.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

/**
 * Used to transfer address objects
 */
@Setter
@Getter
@ToString
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
