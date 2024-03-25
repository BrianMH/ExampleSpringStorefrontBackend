package com.brian.clothingstorefront.repository;

import com.brian.clothingstorefront.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByNotifyBy_UserId(UUID userId);
}
