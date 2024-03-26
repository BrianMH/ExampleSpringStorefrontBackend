package com.brian.clothingstorefront.controller;

import com.brian.clothingstorefront.service.ProductService;
import com.brian.clothingstorefront.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService prodService;

    /**
     * Returns a pagination representing the list of products satisfying the predicate.
     *
     * @param page
     * @param pageSize
     * @param catId
     * @param query
     * @return
     */
    @GetMapping(value = "/paged/{pageSize}/{page}")
    public ResponseEntity<List<ProductDTO>> getPagedProductsWithQueryAndCategory(@PathVariable int page,
                                                                                 @PathVariable int pageSize,
                                                                                 @RequestParam(required = false) Long catId,
                                                                                 @RequestParam String query) {
        // throw an error with problematic inputs
        if (page < 0 || pageSize <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());

        // like before we can just return the paged values
        if (catId != null)
            return ResponseEntity.status(HttpStatus.OK).body(prodService.getOffsetPagedProductsWithCategoryIdAndQuery(page, pageSize, catId, query));
        else
            return ResponseEntity.status(HttpStatus.OK).body(prodService.getOffsetPagedProductsWithQuery(page, pageSize, query));
    }

    /**
     * Returns the number of pages associated with the given query if they were to be passed with a page value to the paged route
     * @param pageSize
     * @param catId
     * @param query
     * @return
     */
    @GetMapping(value = "/paged/{pageSize}")
    public ResponseEntity<Long> getNumberPagesWithQueryAndCategory(@PathVariable int pageSize,
                                                                   @RequestParam(required = false) Long catId,
                                                                   @RequestParam String query) {
        // throw an error with problematic inputs
        if(pageSize < 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((long)0);

        // otherwise return page size
        if (catId != null)
            return ResponseEntity.status(HttpStatus.OK).body(prodService.getNumPages(pageSize, query, catId));
        else
            return ResponseEntity.status(HttpStatus.OK).body(prodService.getNumPages(pageSize, query));
    }
}
