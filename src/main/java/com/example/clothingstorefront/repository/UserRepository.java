package com.example.clothingstorefront.repository;

import com.example.clothingstorefront.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "SELECT COUNT(*) FROM User WHERE username LIKE %:query% OR email LIKE %:query% OR screenName LIKE %:query%"
    )
    public Long findNumPagesWithQuery(String query);

    @Query(
            "FROM User WHERE screenName LIKE %:query% OR email LIKE %:query% OR username LIKE %:query%"
    )
    public List<User> findUsersByQuery(@Param("query") String query);

    @Query(
            "FROM User WHERE screenName LIKE %:query% OR email LIKE %:query% OR username LIKE %:query%"
    )
    public Page<User> findAllByQueryWithPages(@Param("query") String query, Pageable pageable);
}
