package com.example.seo.festivalsendmessages.Beans;

/**
 * Created by Seo on 2016/6/12.
 */
public class RegularlySendBean {
    private String message;
    private String number;
    private String date;

    public RegularlySendBean(String message, String number, String date) {
        this.message = message;
        this.number = number;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
