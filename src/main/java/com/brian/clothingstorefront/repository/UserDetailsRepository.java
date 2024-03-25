package com.brian.clothingstorefront.repository;

import com.brian.clothingstorefront.model.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, UUID> {

}
