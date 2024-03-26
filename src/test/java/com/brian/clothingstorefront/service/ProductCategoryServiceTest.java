package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.ProductCategoryDTO;
import com.brian.clothingstorefront.model.ProdCategory;
import com.brian.clothingstorefront.model.Product;
import com.brian.clothingstorefront.repository.ProdCategoryRepository;
import com.brian.clothingstorefront.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductCategoryServiceTest {

    @TestConfiguration
    static class ProductCategoryServiceTestConfiguration {

        @Bean
        public ProductCategoryService productCategoryService() {
            return new ProductCategoryService();
        }
    }

    @Autowired
    private ProductCategoryService productCategoryService;

    // and like-wise mock dependencies
    @MockBean
    private ProdCategoryRepository prodCategoryRepository;

    @MockBean
    private ProductRepository productRepository;

    // set up some constants for use here
    private static final ProdCategory PROD_CATEGORY = new ProdCategory(0, "cat1", "descr1", "ref1", null);
    private static final Product DEF_PROD = new Product(0, "prod1", "des1", false, 1000, null, PROD_CATEGORY, null);

    @BeforeEach
    public void setUp() {
        Mockito.when(prodCategoryRepository.findById(PROD_CATEGORY.getId())).thenReturn(Optional.of(PROD_CATEGORY));
        Mockito.when(productRepository.getProductsByProdCategory_Id(PROD_CATEGORY.getId())).thenReturn(List.of(DEF_PROD));
    }

    @Test
    public void testGetCategoryById() {
        Optional<ProductCategoryDTO> relReturn = productCategoryService.getCategoryById(PROD_CATEGORY.getId());

        // first make sure object is there
        Assert.assertTrue("There must be a valid return.", relReturn.isPresent());

        // and then test entries
        ProductCategoryDTO exposedObj = relReturn.get();
        Assert.assertTrue("IDs for prod categories must match", exposedObj.getId() == PROD_CATEGORY.getId());
        Assert.assertTrue("Category names must match", exposedObj.getTagName().equals(PROD_CATEGORY.getTagName()));
        Assert.assertTrue("Category descriptions must match", exposedObj.getDescription().equals(PROD_CATEGORY.getDescription()));
        Assert.assertTrue("References must match", exposedObj.getImageRef().equals(PROD_CATEGORY.getImageRef()));
        Assert.assertTrue("There must be only one product under category", exposedObj.getProductList().size() == 1);
        Assert.assertEquals("Products themselves must match", exposedObj.getProductList().getFirst().getId(), DEF_PROD.getId());
    }
}
