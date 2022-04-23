package com.example.ehealthhistory.database.dto;

public class ClubDTO {

    private String cif;
    private boolean active;
    private String alias;
    private String contactname;
    private String name;
    private String president;
    private String username;
    private String username_careteam;

    public ClubDTO() {
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_careteam() {
        return username_careteam;
    }

    public void setUsername_careteam(String username_careteam) {
        this.username_careteam = username_careteam;
    }
}
