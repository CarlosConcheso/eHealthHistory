package com.example.ehealthhistory.database.dto;

public class CareTeamDTO {

    private String name;
    private String note;
    private String status;
    private int telecom;
    private String username;

    public CareTeamDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTelecom() {
        return telecom;
    }

    public void setTelecom(int telecom) {
        this.telecom = telecom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
