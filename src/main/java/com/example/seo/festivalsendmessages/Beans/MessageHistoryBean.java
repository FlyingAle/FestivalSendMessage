package com.example.seo.festivalsendmessages.Beans;

/**
 * Created by Seo on 2016/3/9.
 */
public class MessageHistoryBean {

    private String acceptanceNumber;
    private String contactName;
    private int FestivalId;
    private int MessageId;
    private String date;
    private String message;

    public MessageHistoryBean(String acceptanceNumber, String contactName, int festivalId, int messageId, String date, String message) {
        this.acceptanceNumber = acceptanceNumber;
        this.contactName = contactName;
        FestivalId = festivalId;
        MessageId = messageId;
        this.date = date;
        this.message = message;
    }

    public String getAcceptanceNumber() {
        return acceptanceNumber;
    }

    public void setAcceptanceNumber(String acceptanceNumber) {
        this.acceptanceNumber = acceptanceNumber;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
