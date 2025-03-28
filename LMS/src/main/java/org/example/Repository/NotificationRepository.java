package org.example.Repository;

import org.example.Domain.Notification;
import java.util.HashMap;
import java.util.Map;

public class NotificationRepository {
    private final Map<Integer, Notification> notificationMap = new HashMap<>();

    public void save(Notification notification) {
        notificationMap.put(notification.getNotificationId(), notification);
    }

    public Notification findById(int notificationId) {
        return notificationMap.get(notificationId);
    }

    public void delete(int notificationId) {
        notificationMap.remove(notificationId);
    }
}
