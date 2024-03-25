package com.example.clothingstorefront.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * A representation of a full embodiment of a product that also includes all relevant variants.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @JsonProperty("productId")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("outOfStock")
    private boolean outOfStock;
    @JsonProperty("carouselList")
    private List<String> imageCollection;
    @JsonProperty("priceInCents")
    private long priceInCents;

    @JsonProperty("category")
    private ProductCategoryDTO prodCategory;
}
