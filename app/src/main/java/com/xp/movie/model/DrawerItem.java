package com.xp.movie.model;


/**
 * Created by XP on 2015/5/1.
 */
public class DrawerItem {
    private int itemIconId;
    private String itemName;

    public DrawerItem(int itemIconId, String itemName) {
        this.itemIconId = itemIconId;
        this.itemName = itemName;
    }

    public int getItemIconId() {
        return itemIconId;
    }

    public String getItemName() {
        return itemName;
    }
}
