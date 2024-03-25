package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.repository.ProductRepository;
import com.brian.clothingstorefront.dto.ProductDTO;
import com.brian.clothingstorefront.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository prodRepo;

    /**
     * Adds a product to the database given the DTO representation
     * @param toAdd a DTO representation of the object to add
     * @return
     */
    public ProductDTO addProduct(ProductDTO toAdd) {
        // first convert it
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD)
                .setPreferNestedProperties(false);
        Product converted = converter.map(toAdd, Product.class);

        // then add it to the database
        Product saved = prodRepo.save(converted);
        return converter.map(saved, ProductDTO.class);
    }

    /**
     * Pagination supporting version of a request that takes in a query and category ID to identify potential products
     * @param pageNumber
     * @param pageSize
     * @param catId
     * @param query
     * @return a list of applicable DTOs representing relevant products
     */
    public List<ProductDTO> getOffsetPagedProductsWithCategoryIdAndQuery(int pageNumber, int pageSize, long catId, String query) {
        Page<Product> page = prodRepo.findAllByQueryPageableUnderCategory(query, catId, PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(prod -> converter.map(prod, ProductDTO.class)).toList();
    }

    /**
     * Variant of the above but not using any category Id to slim the possible returns
     * @param pageNumber
     * @param pageSize
     * @param query
     * @return a list of applicable DTOs representing relevant products
     */
    public List<ProductDTO> getOffsetPagedProductsWithQuery(int pageNumber, int pageSize, String query) {
        Page<Product> page = prodRepo.findAllByQueryPageable(query, PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(prod -> converter.map(prod, ProductDTO.class)).toList();
    }

    public Long getNumPages(int pageSize, String query) {
        return (long)Math.ceil(prodRepo.findCountWithQuery(query)/(double)pageSize);
    }

    public Long getNumPages(int pageSize, String query, long catId) {
        return (long)Math.ceil(prodRepo.findCountWithQueryAndCategory(query, catId)/(double)pageSize);
    }
}
