package com.petfinder.ui.chat;

public class ReceivedMessage extends Message {

    private Long time;

    public ReceivedMessage() {
    }

    public ReceivedMessage(Long time) {
        this.time = time;
    }

    public ReceivedMessage(String message, String photoUrl, String name, String profilePicture, String messageType, Long time) {
        super(message, photoUrl, name, profilePicture, messageType);
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
