package com.brian.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
