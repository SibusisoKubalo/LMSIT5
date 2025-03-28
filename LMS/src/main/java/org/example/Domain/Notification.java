package org.example.Domain;

import java.util.Date;

public class Notification {
    private int notificationId;
    private Date dateCreated;
    private String content;

    public Notification(int notificationId, Date dateCreated, String content) {
        this.notificationId = notificationId;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    public void sendNotification() {
        System.out.println("Sending notification: " + content);
    }
}
