package com.example.ehealthhistory.data.model.footballer;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
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

    public enum genders {MALE, FEMALE, OTHER, UNKNOW}

    private String username;
    private boolean active;
    private String name;
    private int telecom;
    private genders gender;
    private String birthdate;

    private FootballerContact footballerContact;
    private FootballerComunication footballerComunication;
    private ArrayList<CareTeam> favsCareTeams;
    private ArrayList<HealthCareService> healthcares;


    public Footballer(){}

    public Footballer(boolean active, String username, String name, int telecom, genders gender, String birthdate,
                      FootballerContact footballerContact, FootballerComunication footballerComunication)
    {
        this.active=active;
        this.username=username;
        this.name=name;
        this.telecom=telecom;
        this.gender=gender;
        this.birthdate=birthdate;
        this.footballerContact=footballerContact;
        this.footballerComunication=footballerComunication;
    }

    public boolean getActive()
    {
        return active;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getTelecom() {
        return telecom;
    }

    public genders getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthdate;
    }

    public FootballerContact getFootballerContact() {
        return footballerContact;
    }

    public FootballerComunication getFootballerComunication() {
        return footballerComunication;
    }

    public ArrayList<CareTeam> getFavsCareTeams() {
        return favsCareTeams;
    }

    public void setFavsCareTeams(ArrayList<CareTeam> favsCareTeams) {
        this.favsCareTeams = favsCareTeams;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<HealthCareService> getHealthcares() {
        return healthcares;
    }

    public void setHealthcares(ArrayList<HealthCareService> healthcares) {
        this.healthcares = healthcares;
    }
}