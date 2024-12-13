package org.models;

import java.time.Instant;

public class Message {
    private int id;
    private Chat chat;
    private User sender;
    private String message;
    private Instant sentAt;
    private boolean isRead;

    public Message(int id, Chat chat, User sender, String message, Instant sentAt, boolean isRead) {
        this.id = id;
        this.chat = chat;
        this.sender = sender;
        this.message = message;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", chat=" + chat +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                ", isRead=" + isRead +
                '}';
    }

    public Message() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
