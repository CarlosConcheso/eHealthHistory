package com.example.ehealthhistory.data.model.User;

import java.util.ArrayList;

public class User {

    private String username;
    private String name;
    private ArrayList<String> rol;

    public User(){
    }

    public User(String username, String name, ArrayList<String> rol) {
        this.username = username;
        this.name = name;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRol() {
        return rol;
    }

    public void setRol(ArrayList<String> rol) {
        this.rol = rol;
    }
}
