package com.example.seo.festivalsendmessages.Beans;

/**
 * Created by Seo on 2016/6/20.
 */
public class SettingItemBean {
    private String itemText;
    private int itemIcon;

    public SettingItemBean(String itemText, int itemIcon) {
        this.itemText = itemText;
        this.itemIcon = itemIcon;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }
}
