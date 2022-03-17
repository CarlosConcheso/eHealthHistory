package com.example.ehealthhistory.data.model.footballer;

import com.example.ehealthhistory.data.model.Club.Club;

/**
 * Contacto del futbolista con diferentes datos de interés para ello.
 */
public class FootballerContact {

    private String name;
    private int telecom;
    private String adress;
    private Club club;

    public FootballerContact(String name, int telecom, String adress, Club club) {
        this.name = name;
        this.telecom = telecom;
        this.adress = adress;
        this.club = club;
    }

    public FootballerContact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelecom() {
        return telecom;
    }

    public void setTelecom(int telecom) {
        this.telecom = telecom;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
