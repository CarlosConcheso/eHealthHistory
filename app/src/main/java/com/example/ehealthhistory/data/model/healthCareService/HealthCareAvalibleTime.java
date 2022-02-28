package com.example.ehealthhistory.data.model.healthCareService;

import java.util.ArrayList;

/**
 * Establecer el tiempo que está disponible el cuidado de un futbolista
 */
public class HealthCareAvalibleTime {


    public enum daysOfWeek { MON, TUE, WED, THU, FRI, SAT, SUN}

    ArrayList<daysOfWeek> daysOfHealthCare;
    private boolean allDay;
    private String avalibleStartTime;
    private String avalibleEndTime;

    public HealthCareAvalibleTime(){}

    public HealthCareAvalibleTime(ArrayList<daysOfWeek> daysOfHealthCare, boolean allDay,
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
}
