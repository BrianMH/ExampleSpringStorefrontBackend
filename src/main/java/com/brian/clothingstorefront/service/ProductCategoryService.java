package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.ProductCategoryDTO;
import com.brian.clothingstorefront.repository.ProdCategoryRepository;
import com.brian.clothingstorefront.repository.ProductRepository;
import com.brian.clothingstorefront.dto.ProductDTO;
import com.brian.clothingstorefront.model.ProdCategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    @Autowired
    private ProdCategoryRepository prodCatRepo;

    @Autowired
    private ProductRepository prodRepo;

    /**
     * Returns the list of product categories in the database
     * @return a list of DTO objects with the categories
     */
    public List<ProductCategoryDTO> getAllCategories() {
        // first get all our product categories
        List<ProdCategory> prodList = prodCatRepo.findAll();

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return prodList.stream().map(cat -> converter.map(cat, ProductCategoryDTO.class)).toList();
    }

    /**
     * Adds a new given product category to the category database
     * @return the DTO representing the new category
     */
    public ProductCategoryDTO addCategory(ProductCategoryDTO toAdd) {
        // convert our DTO to a proper category
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        ProdCategory convertedVal = converter.map(toAdd, ProdCategory.class);

        // persist and return the converted value
        prodCatRepo.save(convertedVal);
        return converter.map(convertedVal, ProductCategoryDTO.class);
    }

    /**
     * Deletes a specific category by its id
     * @param id - the id of the category to delete
     */
    public void deleteCategoryById(long id) {
        prodCatRepo.deleteById(id);
    }

    /**
     * Returns a category received by its ID value, but in a DTO format.
     * @param id the id of the category to return
     * @return a DTO representing the category
     */
    public Optional<ProductCategoryDTO> getCategoryById(long id) {
        // first try getting the normal value
        Optional<ProdCategory> retVal = prodCatRepo.findById(id);

        if(retVal.isEmpty())
            return Optional.empty();

        // but we have to convert it before returning
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        // and populate the product entries with the category
        ProductCategoryDTO converted = converter.map(retVal.get(), ProductCategoryDTO.class);
        converted.setProductList(prodRepo.getProductsByProdCategory_Id(converted.getId()).stream().map(product -> converter.map(product, ProductDTO.class)).toList());
        return Optional.of(converted);
    }
}
