package com.example.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    @JsonProperty("id")
    private long id;

    @JsonProperty("ownedBy")
    private UserDTO notifyBy;

    @JsonProperty("notifyFor")
    private ProductCategoryDTO category;

    @JsonProperty("enabled")
    private Boolean enabled;
}
