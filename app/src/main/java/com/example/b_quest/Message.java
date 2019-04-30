package com.example.b_quest;

//this class define the blue print for the chat messages
public class Message {

    private String user;
    private String message;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Message() {
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
