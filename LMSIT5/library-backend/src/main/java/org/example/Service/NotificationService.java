package org.example.Service;

import org.example.Domain.Notification;
import org.example.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean sendNotification(Notification notification) {
        Notification saved = notificationRepository.save(notification);
        boolean success = saved != null;
        if (success) {
            System.out.println("Notification sent: " + notification.getContent());
        }
        return success;
    }

    public Notification findNotification(int notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    public boolean deleteNotification(int notificationId) {
        if (notificationRepository.existsById(notificationId)) {
            notificationRepository.deleteById(notificationId);
            return true;
        }
        return false;
    }
}