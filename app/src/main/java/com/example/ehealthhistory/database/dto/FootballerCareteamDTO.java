package com.example.ehealthhistory.database.dto;

import java.util.ArrayList;

public class FootballerCareteamDTO {

    private ArrayList<String> username_careteam;
    private String username_footballer;

    public FootballerCareteamDTO() {
    }

    public ArrayList<String> getUsername_careteam() {
        return username_careteam;
    }

    public void setUsername_careteam(ArrayList<String> username_careteam) {
        this.username_careteam = username_careteam;
    }

    public String getUsername_footballer() {
        return username_footballer;
    }

    public void setUsername_footballer(String username_footballer) {
        this.username_footballer = username_footballer;
    }
}
