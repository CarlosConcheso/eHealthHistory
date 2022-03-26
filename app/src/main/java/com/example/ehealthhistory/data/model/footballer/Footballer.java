package com.example.ehealthhistory.data.model.footballer;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Alcance y Uso:
 *         	Datos demográficos y otra información administrativa sobre una persona o animal que recibe atención
 *              u otros servicios relacionados con la salud.
 *         	Información demográfica necesaria para apoyar los procedimientos administrativos, financieros y logísticos.
 */
public class Footballer implements Serializable {

    //public enum genders {MALE, FEMALE, OTHER, UNKNOW}

    private String username;
    private boolean active;
    private String name;
    private int telecom;
    private String gender;
    private String birthday;
    private Club club;

    private FootballerContact footballerContact;
    private FootballerComunication footballerComunication;
    private ArrayList<CareTeam> favsCareTeams;
    private ArrayList<HealthCareService> healthcares;


    public Footballer(){}

    public Footballer(boolean active, String username, String name, int telecom, String gender, String birthday,
                      Club club,
                      FootballerContact footballerContact, FootballerComunication footballerComunication)
    {
        this.active=active;
        this.username=username;
        this.name=name;
        this.telecom=telecom;
        this.gender=gender;
        this.birthday = birthday;
        this.club=club;
        this.footballerContact=footballerContact;
        this.footballerComunication=footballerComunication;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public FootballerContact getFootballerContact() {
        return footballerContact;
    }

    public void setFootballerContact(FootballerContact footballerContact) {
        this.footballerContact = footballerContact;
    }

    public FootballerComunication getFootballerComunication() {
        return footballerComunication;
    }

    public void setFootballerComunication(FootballerComunication footballerComunication) {
        this.footballerComunication = footballerComunication;
    }

    public ArrayList<CareTeam> getFavsCareTeams() {
        return favsCareTeams;
    }

    public void setFavsCareTeams(ArrayList<CareTeam> favsCareTeams) {
        this.favsCareTeams = favsCareTeams;
    }

    public ArrayList<HealthCareService> getHealthcares() {
        return healthcares;
    }

    public void setHealthcares(ArrayList<HealthCareService> healthcares) {
        this.healthcares = healthcares;
    }
}