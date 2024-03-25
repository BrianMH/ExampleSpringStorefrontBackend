package com.brian.clothingstorefront.repository;

import com.brian.clothingstorefront.model.Address;
import com.brian.clothingstorefront.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    public List<Address> findAllByCreatedBy(User createdBy);
}
