package com.petfinder.ui.chat;

import java.util.Map;

public class MessageSend extends Message {
    private Map time;

    public MessageSend() {
    }

    public MessageSend(Map time) {
        this.time = time;
    }

    public MessageSend(String message, String name, String profilePicture, String messageType, Map time) {
        super(message, name, profilePicture, messageType);
        this.time = time;
    }

    public MessageSend(String message, String photoUrl, String name, String profilePicture, String messageType, Map time) {
        super(message, photoUrl, name, profilePicture, messageType);
        this.time = time;
    }

    public Map gettime() {
        return time;
    }

    public void settime(Map time) {
        this.time = time;
    }
}
