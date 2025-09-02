package org.example.Domain;

// Ayrton Williams 220086168

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;
    private Date dateCreated;
    private String content;

    private Notification() {}

    // Builder constructor
    public Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.dateCreated = builder.dateCreated;
        this.content = builder.content;
    }

    // Getters and setters for JPA
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", dateCreated=" + dateCreated +
                ", content='" + content + '\'' +
                '}';
    }

    public static class Builder {
        private int notificationId;
        private Date dateCreated;
        private String content;

        public Builder notificationId(int notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder dateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }

    public void sendNotification() {
        System.out.println("Sending notification: " + content);
    }
}