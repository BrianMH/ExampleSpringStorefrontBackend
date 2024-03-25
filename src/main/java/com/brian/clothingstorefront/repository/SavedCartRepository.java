package com.brian.clothingstorefront.repository;

import com.brian.clothingstorefront.model.SavedCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SavedCartRepository extends JpaRepository<SavedCart, UUID> {

}
