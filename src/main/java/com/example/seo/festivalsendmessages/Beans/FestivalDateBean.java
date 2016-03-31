package com.example.seo.festivalsendmessages.Beans;

/**
 * Created by Seo on 2016/3/9.
 */
public class FestivalDateBean {

    private int FestivalId;
    private String FestivalName;
    private String FestivalDate;

    public FestivalDateBean(int festivalId, String festivalName, String festivalDate) {
        FestivalId = festivalId;
        FestivalName = festivalName;
        FestivalDate = festivalDate;
    }

    public int getFestivalId() {
        return FestivalId;
    }

    public void setFestivalId(int festivalId) {
        FestivalId = festivalId;
    }

    public String getFestivalName() {
        return FestivalName;
    }

    public void setFestivalName(String festivalName) {
        FestivalName = festivalName;
    }

    public String getFestivalDate() {
        return FestivalDate;
    }

    public void setFestivalDate(String festivalDate) {
        FestivalDate = festivalDate;
    }
}
