package com.example.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

/**
 * DTO object for the message entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    @JsonProperty("msgId")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("message")
    private String messageContent;
    @JsonProperty("created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    private Instant created;
}
