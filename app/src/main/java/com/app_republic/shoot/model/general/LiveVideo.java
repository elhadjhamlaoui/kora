package com.app_republic.shoot.model.general;

public class LiveVideo {
    private String team1, team2, href, date;

    public LiveVideo(String team1, String team2, String href, String date) {
        this.team1 = team1;
        this.team2 = team2;
        this.href = href;
        this.date = date;
    }

    public LiveVideo() {

    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
