package com.example.android.testlogin;

/**
 * Created by Soul on 10/31/2017.
 */

public class LastMessage {
    public String text;

    public LastMessage(){

    }

    public LastMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
