package com.example.seo.festivalsendmessages.Beans;

import java.io.Serializable;

/**
 * Created by Seo on 2016/3/9.
 */
public class FestivalMessageBean implements Serializable{

    private int FestivalId;
    private int MessageId;
    private String Message;

    public FestivalMessageBean(int festivalId, int messageId, String message) {
        FestivalId = festivalId;
        MessageId = messageId;
        Message = message;
    }

    public int getFestivalId() {
        return FestivalId;
    }

    public void setFestivalId(int festivalId) {
        FestivalId = festivalId;
    }

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
