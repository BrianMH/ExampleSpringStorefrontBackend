package com.brian.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String tagName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageRef")
    private String imageRef;

    // We ignore this as we will NEVER set all products in a category and so we don't want to actually read it
    // BUT, we would like to transfer it whenever possible
    @JsonProperty("relProducts")
    private List<ProductDTO> productList;
}
