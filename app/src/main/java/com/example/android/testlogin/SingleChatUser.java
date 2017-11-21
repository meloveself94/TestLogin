package com.example.android.testlogin;



/**
 * Created by Soul on 10/30/2017.
 */

public class SingleChatUser {

    public String name;
    public String lastMessage;
    public String targetPic;

    public SingleChatUser() {

    }


    public SingleChatUser(String name, String lastMessage, String targetPic) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.targetPic = targetPic;
    }


    public String getTheName() {
        return name;
    }

    public void setTheName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTargetPic() {
        return targetPic;
    }

    public void setTargetPic(String targetPic) {
        this.targetPic = targetPic;
    }
}
