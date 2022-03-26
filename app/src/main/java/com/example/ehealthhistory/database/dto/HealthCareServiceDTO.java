package com.example.ehealthhistory.database.dto;

import java.util.ArrayList;

public class HealthCareServiceDTO {

    private boolean active;
    private boolean avalibleTime_allDay;
    private ArrayList<String> avalibleTime_daysOfHealthCare;
    private String avalibleTime_startTime;
    private String avalibleTime_endTime;
    private String category;
    private String commentary;
    private String extraDetails;
    private String name;
    private String username;

    public HealthCareServiceDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAvalibleTime_allDay() {
        return avalibleTime_allDay;
    }

    public void setAvalibleTime_allDay(boolean avalibleTime_allDay) {
        this.avalibleTime_allDay = avalibleTime_allDay;
    }

    public ArrayList<String> getAvalibleTime_daysOfHealthCare() {
        return avalibleTime_daysOfHealthCare;
    }

    public void setAvalibleTime_daysOfHealthCare(ArrayList<String> avalibleTime_daysOfHealthCare) {
        this.avalibleTime_daysOfHealthCare = avalibleTime_daysOfHealthCare;
    }

    public String getAvalibleTime_startTime() {
        return avalibleTime_startTime;
    }

    public void setAvalibleTime_startTime(String avalibleTime_startTime) {
        this.avalibleTime_startTime = avalibleTime_startTime;
    }

    public String getAvalibleTime_endTime() {
        return avalibleTime_endTime;
    }

    public void setAvalibleTime_endTime(String avalibleTime_endTime) {
        this.avalibleTime_endTime = avalibleTime_endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
