package com.app_republic.kora.model;

public class MatchDetail {
    private String label, content;
    private int icon;

    public MatchDetail(String label, String content, int icon) {
        this.label = label;
        this.content = content;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
