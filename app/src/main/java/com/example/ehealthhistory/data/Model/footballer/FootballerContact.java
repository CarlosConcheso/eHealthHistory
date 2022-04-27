package com.example.ehealthhistory.data.Model.footballer;

import com.example.ehealthhistory.data.Model.Club.Club;

/**
 * Contacto del futbolista con diferentes datos de interés para ello.
 */
public class FootballerContact {

    private String name;
    private int telecom;
    private String address;
    private Club club;

    public FootballerContact(String name, int telecom, String address, Club club) {
        this.name = name;
        this.telecom = telecom;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
