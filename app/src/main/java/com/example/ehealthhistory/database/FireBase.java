package com.example.ehealthhistory.database;

import android.util.Log;

import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerComunication;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FireBase {

    private final FirebaseFirestore db;

    public boolean semaforo = false;

    public FireBase()
    {
        db = FirebaseFirestore.getInstance();
    }

    public String getName(String username) {
        final String[] name = {""};

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            name[0] = Objects.requireNonNull(document.getData().get("name")).toString();
                        }
                    }
                });
        return name[0];
    }

    public ArrayList<String> getRolesOfUsername(String username) {

        final ArrayList<String> rolesFinales = new ArrayList<>();

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            String[] roles = (String[]) document.getData().get("rol");

                            if(roles != null)
                                Collections.addAll(rolesFinales, roles);

                        }
                    }
                });
        return rolesFinales;
    }

    public Footballer getFootballer(String username) {

        semaforo = false;

        Footballer footballer = new Footballer();
        FootballerContact fc = new FootballerContact();
        FootballerComunication fcom = new FootballerComunication();

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
}