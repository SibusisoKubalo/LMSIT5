package org.example.Service;

import org.example.Domain.Notification;
import org.example.Repository.NotificationRepository;

public class NotificationService {
    private final NotificationRepository notificationRepository = new NotificationRepository();

    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
        System.out.println("Notification sent: " + notification.getContent());
    }

    public Notification findNotification(int notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public void deleteNotification(int notificationId) {
        notificationRepository.delete(notificationId);
    }
}