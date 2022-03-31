package com.example.ehealthhistory.database.dto;

import java.util.ArrayList;

public class FootballerDTO {

    private boolean active;
    private String birthday;
    private ArrayList<String> careteams;
    private String club;
    private String comunication_lenguage;
    private boolean comunication_prefered;
    private String contact_address;
    private String contact_name;
    private String contact_telecom;
    private String gender;
    private String name;
    private String telecom;
    private String username;

    public FootballerDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ArrayList<String> getCareteams() {
        return careteams;
    }

    public void setCareteams(ArrayList<String> careteams) {
        this.careteams = careteams;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getComunication_lenguage() {
        return comunication_lenguage;
    }

    public void setComunication_lenguage(String comunication_lenguage) {
        this.comunication_lenguage = comunication_lenguage;
    }

    public void setComunication_prefered(boolean comunication_prefered) {
        this.comunication_prefered = comunication_prefered;
    }

    public boolean isComunication_prefered() {
        return comunication_prefered;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_telecom() {
        return contact_telecom;
    }

    public void setContact_telecom(String contact_telecom) {
        this.contact_telecom = contact_telecom;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelecom() {
        return telecom;
    }

    public void setTelecom(String telecom) {
        this.telecom = telecom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
