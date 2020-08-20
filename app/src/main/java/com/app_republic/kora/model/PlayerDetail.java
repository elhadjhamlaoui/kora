package com.app_republic.kora.model;

public class PlayerDetail {
    private String name, count;
    private int icon;

    public PlayerDetail(String name, String count, int icon) {
        this.name = name;
        this.count = count;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
