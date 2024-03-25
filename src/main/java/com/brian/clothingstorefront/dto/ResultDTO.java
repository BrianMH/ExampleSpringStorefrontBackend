package com.brian.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Provides a way for the REST API to communicate with any systems that are using the API via result message strings
 * and a boolean flag.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    // And this holds any actual errors
    @JsonProperty("operationSuccess")
    private boolean success;

    // This holds a potential response
    @JsonProperty("message")
    private String message;
}
