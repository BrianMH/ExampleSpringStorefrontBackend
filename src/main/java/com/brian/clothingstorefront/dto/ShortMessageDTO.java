package com.brian.clothingstorefront.dto;

import com.brian.clothingstorefront.dto.utils.DescriptionSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO object for the message entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortMessageDTO {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("shortMessage")
    @JsonSerialize(using = DescriptionSerializer.class)
    private String messageContent;
}
