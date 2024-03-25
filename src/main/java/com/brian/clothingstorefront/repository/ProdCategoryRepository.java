package com.brian.clothingstorefront.repository;

import com.brian.clothingstorefront.model.ProdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdCategoryRepository extends JpaRepository<ProdCategory, Long> {

}
