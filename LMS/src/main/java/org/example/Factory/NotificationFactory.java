package org.example.Factory;

import org.example.Domain.Notification;

import java.util.Date;

public class NotificationFactory {
    public static Notification createNotification(int notificationId, String content) {
        return new Notification(notificationId, new Date(), content);
    }
}

