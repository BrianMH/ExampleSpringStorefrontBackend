package com.brian.clothingstorefront.controller;

import com.brian.clothingstorefront.dto.ProductCategoryDTO;
import com.brian.clothingstorefront.dto.ResultDTO;
import com.brian.clothingstorefront.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService prodCatSvc;

    /**
     * Returns all values from the database
     * @return a list of categories that are present in the DB
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(prodCatSvc.getAllCategories());
    }

    /**
     * Adds a value to the database
     * @param toAdd the value to add
     * @return a DTO converted version of the value
     */
    @PostMapping("/add")
    public ResponseEntity<?> addNewCategory(@RequestBody ProductCategoryDTO toAdd) {
        try {
            ProductCategoryDTO addedVal = prodCatSvc.addCategory(toAdd);
            return ResponseEntity.status(HttpStatus.OK).body(addedVal);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO(false, e.getMessage()));
        }
    }

    /**
     * Patches a specific entry given the product category passed
     * @param toModify the DTO containing the id to modify and potential changes
     * @return a result DTO representing the status of the operation
     */
    @PatchMapping("/patch")
    public ResponseEntity<ResultDTO> patchCategory(@RequestBody ProductCategoryDTO toModify) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResultDTO(false, "Not implemented."));
    }

    /**
     * Gets a given product category and associated products via the product category ID
     *
     * @param id the id of the category to return
     * @return a DTO representing either the category or some associated error if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id) {
        Optional<ProductCategoryDTO> retVal = prodCatSvc.getCategoryById(id);
        if(retVal.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Category ID not found"));

        return ResponseEntity.status(HttpStatus.OK).body(retVal.get());
    }

    /**
     * Deletes specified category by its id
     * @param id the id of the category to delete
     * @return a DTO representing the operation status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteCategoryById(@PathVariable long id) {
        try {
            prodCatSvc.deleteCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, "Deleted"));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, e.getMessage()));
        }
    }
}
