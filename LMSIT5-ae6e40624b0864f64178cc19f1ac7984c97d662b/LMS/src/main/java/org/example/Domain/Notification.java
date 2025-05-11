package org.example.Domain;

//Ayrton Williams 220086168

import java.util.Date;

public class Notification {
    private int notificationId;
    private Date dateCreated;
    private String content;

    private Notification(){
    }

    public Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.dateCreated = builder.dateCreated;
        this.content = builder.content;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", createOn=" + createOn +
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
