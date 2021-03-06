package com.example.ehealthhistory.data.Model.healthCareService;

import java.util.ArrayList;

/**
 * Establecer el tiempo que está disponible el cuidado de un futbolista
 */
public class HealthCareAvalibleTime {


    ArrayList<String> daysOfHealthCare;
    private boolean allDay;
    private String avalibleStartTime;
    private String avalibleEndTime;

    public HealthCareAvalibleTime(){}

    public HealthCareAvalibleTime(ArrayList<String> daysOfHealthCare, boolean allDay,
                                  String avalibleStartTime, String avalibleEndTime)
    {
        this.daysOfHealthCare = daysOfHealthCare;
        this.allDay = allDay;
        this.avalibleStartTime = avalibleStartTime;
        this.avalibleEndTime = avalibleEndTime;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getAvalibleStartTime() {
        return avalibleStartTime;
    }

    public void setAvalibleStartTime(String avalibleStartTime) {
        this.avalibleStartTime = avalibleStartTime;
    }

    public String getAvalibleEndTime() {
        return avalibleEndTime;
    }

    public void setAvalibleEndTime(String avalibleEndTime) {
        this.avalibleEndTime = avalibleEndTime;
    }

    public ArrayList<String> getDaysOfHealthCare() {
        return daysOfHealthCare;
    }

    public void setDaysOfHealthCare(ArrayList<String> daysOfHealthCare) {
        this.daysOfHealthCare = daysOfHealthCare;
    }
}
