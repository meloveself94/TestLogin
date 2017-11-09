package com.example.android.testlogin;

/**
 * Created by Soul on 9/26/2017.
 */

public class ChatMessage {

    private String text;
    private String name;
    private String isUnread;
    private String from;


    public ChatMessage() {
    }

    public ChatMessage(String text, String name , String isUnread , String from) {
        this.text = text;
        this.name = name;
        this.isUnread = isUnread;
        this.from = from;

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

    public String getIsUnread() {
        return isUnread;
    }

    public void setUnread(String isUnread) {
        this.isUnread = isUnread;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
