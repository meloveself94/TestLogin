package com.example.android.testlogin;

/**
 * Created by Soul on 9/26/2017.
 */

public class ChatMessage {

    private String text;
    private String name;
    private String photoUrl;

    public ChatMessage() {
    }

    public ChatMessage(String text, String username, String photoUrl) {
        this.text = text;
        this.name = username;
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
