package com.brian.clothingstorefront.repository;

import com.brian.clothingstorefront.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByOrderByDateCreatedDesc(Pageable pageable);

    @Query(
            "FROM Message WHERE name LIKE %:query% OR email LIKE %:query% OR subject LIKE %:query%"
    )
    Page<Message> findAllByOrderByDateCreatedDescWithQuery(@Param("query")String query, Pageable pageable);

    @Query(
            "SELECT COUNT(*) FROM Message WHERE name LIKE %:query% OR email LIKE %:query% OR subject LIKE %:query%"
    )
    long findNumPagesWithQuery(@Param("query")String query);
}
