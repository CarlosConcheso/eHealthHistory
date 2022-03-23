package com.example.ehealthhistory.database;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.footballer.FootballerComunication;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.example.ehealthhistory.ui.Foootballer.UIFootballerContact;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.Objects;

public class FireBase {

    private final FirebaseFirestore db;

    public FireBase()
    {
        db = FirebaseFirestore.getInstance();
    }

    // ---------- Meodos pantalla inicial roles
    @SuppressLint("SetTextI18n")
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

    // ---------- Meodos pantalla principal futbolista
    public void representFootballerBasicData(String username,
                                             TextView footballerName, TextView footballerBirthDay,
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
        ArrayList<HealthCareService> healthcares = new ArrayList<>();

        db.collection("healthcare")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            HealthCareService hc = new HealthCareService();

                            hc.setActive(Boolean.parseBoolean(Objects.requireNonNull(document.getData().get("active")).toString()));
                            hc.setCategory(document.get("category").toString());
                            healthcares.add(hc);
                        }

                        mainFootballer.addHealthCareRows(healthcares);
                    }
                });
    }

    // Métodos datos concretos futbolista
    public void representFootballerContact(String username,
                                           TextView footballerContactName, TextView footballerContactTelf,
                                           TextView footballerContactLenguaje, TextView footballerContactAdress) {

        FootballerContact fc = new FootballerContact();
        FootballerComunication fcom = new FootballerComunication();

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            fc.setName(Objects.requireNonNull(document.getString("contact_name")));
                            fc.setAdress(Objects.requireNonNull(document.getString("contact_address")));
                            fc.setTelecom(Integer.parseInt(Objects.requireNonNull(document.getString("contact_telecom"))));
                            fcom.setLenguage(Objects.requireNonNull(document.getString("comunication_lenguage")));

                            footballerContactName.setText(fc.getName());
                            footballerContactTelf.setText(String.valueOf(fc.getTelecom()));
                            footballerContactLenguaje.setText(fcom.getLenguage());
                            footballerContactAdress.setText(fc.getAdress());
                        }
                    }
                });
    }


    public void representFootballerClubContact(String username, TextView footballerClubName,
                                               TextView footballerClubAlias, TextView footballerClubContactName,
                                               UIFootballerContact footballerContact) {
        Club club = new Club();

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            String nameOfClub = document.getString("club");

                            db.collection("club")
                                    .whereEqualTo("name", nameOfClub)
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task.getResult())) {

                                                club.setUsername(document2.getString("username"));
                                                club.setName(document2.getString("name"));
                                                club.setAlias(document2.getString("alias"));
                                                club.setContactName(document2.getString("contactname"));

                                                footballerClubName.setText(club.getName());
                                                footballerClubAlias.setText(club.getAlias());
                                                footballerClubContactName.setText(club.getContactName());

                                                footballerContact.setClubUsername(club.getUsername());
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void representFootballerClubCareTeamContact(String clubUsername,
                                                           TextView footballerClubTeamCareName, TextView footballerClubTeamCareTelecom,
                                                           TextView footballerClubTeamCareNote) {
        CareTeam ct = new CareTeam();

        db.collection("club_careteam")
                .whereEqualTo("username_club", clubUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            String careTeamUsername = document.getString("careteam_username");

                            db.collection("careteam")
                                    .whereEqualTo("username", careTeamUsername)
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task.getResult())) {
                                                ct.setName(document2.getString("name"));
                                                ct.setNote(document2.getString("note"));
                                                ct.setStatus(document2.getString("status"));
                                                ct.setTelecom(Integer.parseInt(Objects.requireNonNull(document2.getData().get("telecom")).toString()));

                                                footballerClubTeamCareName.setText(ct.getName());
                                                footballerClubTeamCareTelecom.setText(ct.getTelcom());
                                                footballerClubTeamCareNote.setText(ct.getNote());
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}