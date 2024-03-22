package com.example.clothingstorefront.repository;

import com.example.clothingstorefront.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    public User getUserByUserId(UUID uuid);
    public Optional<User> findByEmail(String email);

    @Query(
            "FROM User WHERE screenName LIKE %:query% OR email LIKE %:query% OR username LIKE %:query%"
    )
    public List<User> findUsersByQuery(@Param("query") String query);
}
