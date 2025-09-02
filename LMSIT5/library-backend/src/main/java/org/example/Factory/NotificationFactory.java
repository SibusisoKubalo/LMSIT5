package org.example.Factory;

// Ayrton Williams 220086168

import org.example.Domain.Notification;

public class NotificationFactory {

    public static Notification validateAndCreateNotification(int notificationId, String content) {
        if (notificationId <= 0 || notificationId > 1000)
            throw new IllegalArgumentException("Notification ID must be between 1 and 1000");
        if (content == null || content.trim().isEmpty() || content.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Content cannot be empty or 'null'");
        return new Notification.Builder()
                .notificationId(notificationId)
                .content(content)
                .build();
    }

    public static Notification createNotificationWithContent(String content) {
        if (content == null || content.trim().isEmpty() || content.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Content cannot be empty or 'null'");
        return new Notification.Builder()
                .content(content)
                .build();
    }

    public static Notification createEmptyNotification() {
        return new Notification.Builder().build();
    }
}