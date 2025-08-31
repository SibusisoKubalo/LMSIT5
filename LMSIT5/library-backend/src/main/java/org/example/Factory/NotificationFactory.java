package org.example.Factory;

//Ayrton Williams 220086168

import org.example.Domain.Notification;

import java.util.Date;

public class NotificationFactory {

    public static Notification ValidateAndCreateNotification(int notificationId, String content) {

        if (notificationId <= 0 || notificationId > 1000) // this number can be changed if needed
            return null;
        if (content.isEmpty() || content.equals("") || content.equals("null"))
            return null;

        return new Notification.Builder()
                .notificationId(notificationId)
                .content(content)
                .build();
    }
}
