package com.example.sdafoodbuddy;

public class Matches {

    private String matchId;
    private int matchTime;
    private String matchGender;
    private String name;
    private String description;
    private String location;
    private boolean createdActivity;




    public Matches(String matchId, int matchTime, String matchGender, String name, String description, String location, boolean createdActivity) {
        this.matchId = matchId;
        this.matchTime = matchTime;
        this.matchGender = matchGender;
        this.name=name;
        this.description = description;
        this.location = location;
        this.createdActivity = createdActivity;

    }

    public String getMatchId() {
        return matchId;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public String getMatchGender() {
        return matchGender;
    }

    public String getMatchName()
    {
        return name;

    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public boolean isCreatedActivity() {
        return createdActivity;
    }



}
