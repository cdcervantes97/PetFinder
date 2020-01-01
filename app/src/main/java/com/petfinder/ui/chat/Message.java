package com.petfinder.ui.chat;

public class Message {
    private String message;
    private String photoUrl;
    private String name;
    private String profilePicture;
    private String messageType;

    public Message() {
    }

    public Message(String message, String name, String profilePicture, String messageType) {
        this.message = message;
        this.name = name;
        this.profilePicture = profilePicture;
        this.messageType = messageType;
    }

    public Message(String message, String photoUrl, String name, String profilePicture, String messageType) {
        this.message = message;
        this.photoUrl = photoUrl;
        this.name = name;
        this.profilePicture = profilePicture;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
