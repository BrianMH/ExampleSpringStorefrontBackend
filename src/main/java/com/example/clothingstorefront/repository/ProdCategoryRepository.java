package com.example.clothingstorefront.repository;

import com.example.clothingstorefront.model.ProdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdCategoryRepository extends JpaRepository<ProdCategory, Long> {

}
