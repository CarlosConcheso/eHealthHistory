package com.example.ehealthhistory.data.model.Club;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.footballer.Footballer;

import java.util.ArrayList;

/**
 * o	Alcance y Uso:
 *      	Puede usarse en un registro compartido de contactos y otra información para varias organizaciones.
 *      	Puede usarse como soporte para otros recursos que necesitan referenciar organizaciones.
 *          Un documento , mensaje o como un recurso contenido.
 */
public class Club {

    private String username;
    private boolean active;
    private String name;
    private String president;
    private String alias;
    private String contactName;
    private CareTeam clubCareTeam;

    private ArrayList<Footballer> footballers;

    public Club(){}

    public Club(String username, boolean active, String name, String president, String alias, String contactName,
                CareTeam clubCareTeam) {
        this.username = username;
        this.active = active;
        this.name = name;
        this.president=president;
        this.alias = alias;
        this.contactName = contactName;
        this.clubCareTeam=clubCareTeam;
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

    public String getPresidente() {
        return president;
    }

    public void setPresidente(String president) {
        this.president = president;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public CareTeam getClubCareTeam() {
        return clubCareTeam;
    }

    public void setClubCareTeam(CareTeam clubCareTeam) {
        this.clubCareTeam = clubCareTeam;
    }

    public ArrayList<Footballer> getFootballers() {
        return footballers;
    }

    public void setFootballers(ArrayList<Footballer> footballers) {
        this.footballers = footballers;
    }

}