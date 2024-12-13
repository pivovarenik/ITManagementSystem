package org.models;

import java.time.Instant;

public class Chat {
    private int id;
    private User userOne;
    private User userTwo;
    private Instant createdAt;

    public int getId() {
        return id;
    }

    public Chat(int id, User userOne, User userTwo, Instant createdAt) {
        this.id = id;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Chat() {
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", userOne=" + userOne +
                ", userTwo=" + userTwo +
                ", createdAt=" + createdAt +
                '}';
    }

    public User getUserOne() {
        return userOne;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }
}
