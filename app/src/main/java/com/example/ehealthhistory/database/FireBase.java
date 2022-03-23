package com.example.ehealthhistory.database;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerComunication;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareAvalibleTime;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FireBase {

    private final FirebaseFirestore db;

    public FireBase()
    {
        db = FirebaseFirestore.getInstance();
    }

    public void getName(String username, TextView tv) {

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String name = document.getString("name");
                            tv.setText("Bienvenido " + name);
                        }
                    }
                });
    }



    public void getRolesOfUsername(String username, Button botonFutbolista, Button botonMedico, Button botonClub) {

        final ArrayList<String> rolesFinales = new ArrayList<>();

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            rolesFinales.add(document.get("rol").toString());
                        }

                        for(int i=0; i<rolesFinales.size(); i++) {
                            if (rolesFinales.get(i).contains("club"))
                                botonClub.setVisibility(View.VISIBLE);

                            if (rolesFinales.get(i).contains("footballer"))
                                botonFutbolista.setVisibility(View.VISIBLE);

                            if (rolesFinales.get(i).contains("careteam"))
                                botonMedico.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public Footballer getFootballer(String username) {

        Footballer footballer = new Footballer();
        FootballerContact fc = new FootballerContact();
        FootballerComunication fcom = new FootballerComunication();

        footballer.setFootballerContact(fc);
        footballer.setFootballerComunication(fcom);

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            footballer.setUsername(Objects.requireNonNull(document.getData().get("username")).toString());
                            footballer.setActive(Boolean.parseBoolean(Objects.requireNonNull(document.getData().get("active")).toString()));
                            footballer.setName(Objects.requireNonNull(document.getData().get("name")).toString());
                            footballer.setTelecom(Integer.parseInt(Objects.requireNonNull(document.getData().get("telecom")).toString()));
                            footballer.setName(Objects.requireNonNull(document.getData().get("birthday")).toString());
                            footballer.setGender(Objects.requireNonNull(document.getData().get("gender")).toString());

                            fc.setName(Objects.requireNonNull(document.getData().get("contact_name")).toString());
                            fc.setAdress(Objects.requireNonNull(document.getData().get("contact_address")).toString());
                            fc.setTelecom(Integer.parseInt(Objects.requireNonNull(document.getData().get("contact_telecom")).toString()));
                            footballer.setFootballerContact(fc);

                            fcom.setPrefered(Boolean.parseBoolean(Objects.requireNonNull(document.getData().get("comunication_prefered")).toString()));
                            fcom.setLenguage(Objects.requireNonNull(document.getData().get("comunication_lenguage")).toString());
                        }
                    }
                });
        return footballer;
    }

    public void representFootballerBasicData(String username,
                                             TextView footballerName,
                                             TextView footballerBirthDay,
                                             TextView footballerTelcom) {

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            footballerName.setText(document.getString("name"));
                            footballerBirthDay.setText(document.getString("birthday"));
                            footballerTelcom.setText(document.getString("telecom"));
                        }
                    }
                });
    }

    public void representBasicFotballerHealthCares(String username, MainFootballer mainFootballer)
    {
        MainFootballer mf = mainFootballer;

        db.collection("healthcare")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {

                    ArrayList<HealthCareService> healthcares = new ArrayList<>();

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            HealthCareService hc = new HealthCareService();

                            hc.setActive(Boolean.parseBoolean(Objects.requireNonNull(document.getData().get("active")).toString()));
                            hc.setCategory(document.get("category").toString());
                            healthcares.add(hc);

                            System.out.println("HEALTH: CATEGORY -> " + hc.getCategory());
                        }
                        System.out.println("HEALTH -> Vamonos a dibujar");

                        mf.addHealthCareRows(healthcares);

                    }
                });
    }

    public Club getFootballerClub(String username) {

        String[] nameOfClub = {""};
        Club club = new Club();

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            nameOfClub [0] = document.getString("club");
                        }
                    }
                });

        db.collection("club")
                .whereEqualTo("name", nameOfClub[0])
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            club.setUsername(document.getString("username"));
                            club.setActive(Boolean.parseBoolean(Objects.requireNonNull(document.getData().get("active")).toString()));
                            club.setName(document.getString("name"));
                            club.setPresidente(document.getString("president"));
                            club.setAlias(document.getString("alias"));
                            club.setContactName(document.getString("contactname"));
                        }
                    }
                });
        return club;
    }

    public CareTeam getClubCareTeam(String clubUsername) {

        final String[] careTeamUsername = new String[1];
        CareTeam ct = new CareTeam();

        db.collection("club_careteam")
                .whereEqualTo("username_club", clubUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            careTeamUsername[0] = document.getString("careteam_username");
                        }
                    }
                });

        db.collection("careteam")
                .whereEqualTo("username", careTeamUsername[0])
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            ct.setName(document.getString("name"));
                            ct.setNote(document.getString("note"));
                            ct.setStatus(document.getString("status"));
                            ct.setTelecom(Integer.parseInt(Objects.requireNonNull(document.getData().get("telecom")).toString()));
                        }
                    }
                });

        return ct;
    }
}