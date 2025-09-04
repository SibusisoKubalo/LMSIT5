package org.example.Repository;

import org.example.Domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // Add custom query methods if needed, e.g.:
    // Optional<Notification> findByContent(String content);
}
