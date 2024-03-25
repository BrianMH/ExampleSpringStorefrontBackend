package com.example.clothingstorefront.repository;

import com.example.clothingstorefront.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> getProductsByProdCategory_Id(long prodCategoryId);

    @Query(
            "FROM Product WHERE name LIKE %:query% OR description LIKE %:query%"
    )
    Page<Product> findAllByQueryPageable(@Param("query")String query, Pageable pageable);

    @Query(
            "FROM Product WHERE ((name LIKE %:query% OR description LIKE %:query%) AND prodCategory.id = :categoryId)"
    )
    Page<Product> findAllByQueryPageableUnderCategory(@Param("query")String query, @Param("categoryId")Long catId, Pageable pageable);

    @Query(
            "SELECT COUNT(*) FROM Product WHERE name LIKE %:query% OR description LIKE %:query%"
    )
    Long findCountWithQuery(@Param("query")String query);


    @Query(
            "SELECT COUNT(*) FROM Product WHERE ((name LIKE %:query% OR description LIKE %:query%) AND prodCategory.id = :categoryId)"
    )
    Long findCountWithQueryAndCategory(@Param("query")String query, @Param("categoryId")Long catId);
}
