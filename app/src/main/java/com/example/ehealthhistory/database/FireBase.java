package com.example.ehealthhistory.database;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.database.dto.CareTeamDTO;
import com.example.ehealthhistory.database.dto.ClubDTO;
import com.example.ehealthhistory.database.dto.FootballerDTO;
import com.example.ehealthhistory.database.dto.HealthCareServiceDTO;
import com.example.ehealthhistory.database.dto.UserDTO;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareAvalibleTime;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.example.ehealthhistory.ui.Foootballer.UIFootballerFavsTeamCares;
import com.example.ehealthhistory.ui.Foootballer.UIFootballerHealthCares;
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
                            UserDTO userDTO = document.toObject(UserDTO.class);

                            tv.setText("Bienvenido " + userDTO.getName());
                        }
                    }
                });
    }

    public void getRolesOfUsername(String username, Button botonFutbolista, Button botonMedico, Button botonClub) {

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            UserDTO userDTO = document.toObject(UserDTO.class);

                            for(int i=0; i<userDTO.getRol().size(); i++) {
                                if (userDTO.getRol().get(i).equals("club"))
                                    botonClub.setVisibility(View.VISIBLE);

                                if (userDTO.getRol().get(i).equals("footballer"))
                                    botonFutbolista.setVisibility(View.VISIBLE);

                                if (userDTO.getRol().get(i).equals("careteam"))
                                    botonMedico.setVisibility(View.VISIBLE);
                            }
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
                            FootballerDTO footballer = document.toObject(FootballerDTO.class);

                            footballerName.setText(footballer.getName());
                            footballerBirthDay.setText(footballer.getBirthday());
                            footballerTelcom.setText(footballer.getTelecom());
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
                            HealthCareServiceDTO healthCareServiceDTO = document.toObject(HealthCareServiceDTO.class);
                            HealthCareService hc = new HealthCareService();

                            hc.setActive(healthCareServiceDTO.isActive());
                            hc.setCategory(healthCareServiceDTO.getCategory());

                            healthcares.add(hc);
                        }

                        mainFootballer.addHealthCareRows(healthcares);
                    }
                });
    }

    // Métodos datos concretos futbolista
    public void representAllFootballerContact(String username,
                                              TextView footballerContactName, TextView footballerContactTelf,
                                              TextView footballerContactLenguaje, TextView footballerContactAdress,
                                              TextView footballerClubName, TextView footballerClubAlias, TextView footballerClubContactName,
                                              TextView footballerClubTeamCareName, TextView footballerClubTeamCareTelecom,
                                              TextView footballerClubTeamCareNote) {

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);

                            footballerContactName.setText(footballerDTO.getContact_name());
                            footballerContactTelf.setText(footballerDTO.getTelecom());
                            footballerContactLenguaje.setText(footballerDTO.getComunication_lenguage());
                            footballerContactAdress.setText(footballerDTO.getContact_address());

                            db.collection("club")
                                    .whereEqualTo("name", footballerDTO.getClub())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                ClubDTO clubDTO = document2.toObject(ClubDTO.class);

                                                footballerClubName.setText(clubDTO.getName());
                                                footballerClubAlias.setText(clubDTO.getAlias());
                                                footballerClubContactName.setText(clubDTO.getContactname());

                                                db.collection("careteam")
                                                        .whereEqualTo("username", clubDTO.getUsername_careteam())
                                                        .get()
                                                        .addOnCompleteListener(task3 -> {
                                                            if (task3.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document3 : Objects.requireNonNull(task3.getResult())) {

                                                                    CareTeamDTO careteamDTO = document3.toObject(CareTeamDTO.class);

                                                                    footballerClubTeamCareName.setText(careteamDTO.getName());
                                                                    footballerClubTeamCareTelecom.setText(String.valueOf(careteamDTO.getTelecom()));
                                                                    footballerClubTeamCareNote.setText(careteamDTO.getNote());
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    //Métodos ver datos de parte futbolista
    public void rellenarSpinnerHealthcareFootballer(String username, Spinner spinner,
                                                    UIFootballerHealthCares uiFootballerHealthCares,
                                                    TextView healthCareCategory, TextView healthCareName,
                                                    TextView healthCareCommentary,TextView healthCareAllDay,
                                                    TextView healthCareHoraInicio, TextView healthCareHoraFin,
                                                    TextView healthCareNote) {

        ArrayList<HealthCareService> lista = new ArrayList<>();

        db.collection("healthcare")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        int i=1;

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            HealthCareServiceDTO healthCareServiceDTO = document.toObject(HealthCareServiceDTO.class);

                            HealthCareService hc = new HealthCareService();
                            HealthCareAvalibleTime hcat = new HealthCareAvalibleTime();

                            hc.setId(i);
                            hc.setUsername(healthCareServiceDTO.getUsername());
                            hc.setName(healthCareServiceDTO.getName());
                            hc.setCategory(healthCareServiceDTO.getCategory());
                            hc.setActive(healthCareServiceDTO.isActive());
                            hc.setCommentary(healthCareServiceDTO.getCommentary());
                            hc.setExtraDetails(healthCareServiceDTO.getExtraDetails());

                            hcat.setAvalibleStartTime(healthCareServiceDTO.getAvalibleTime_startTime());
                            hcat.setAvalibleEndTime(healthCareServiceDTO.getAvalibleTime_endTime());
                            hcat.setAllDay(healthCareServiceDTO.isAvalibleTime_allDay());

                            hcat.setDaysOfHealthCare(healthCareServiceDTO.getAvalibleTime_daysOfHealthCare());
                            hc.setAvalibleTime(hcat);

                            lista.add(hc);
                            i++;
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                uiFootballerHealthCares,
                                android.R.layout.simple_spinner_dropdown_item,
                                uiFootballerHealthCares.convert2ArrayHealthCares(lista));

                        spinner.setAdapter(adapter);
                        uiFootballerHealthCares.setHealthCares(lista);

                        uiFootballerHealthCares.representarValorSpinnerInicial(
                                healthCareCategory, healthCareName,
                                healthCareCommentary, healthCareAllDay,
                                healthCareHoraInicio, healthCareHoraFin,
                                healthCareNote);

                        uiFootballerHealthCares.setConsulta(true);
                    }
                });
    }

    // Métodos añadir médico fav futbolista
    public void representarFootballerCareTeamsNoFav()
    {

    }

    public void representarFootballerFavsCareTeams(String username, Spinner spinnerFavCareTeam, TextView favCareTeamName,
                                                   TextView favCareTeamStatus,TextView favCareTeamTelecom,
                                                   TextView favCareTeamNote, UIFootballerFavsTeamCares uiFootballerFavsTeamCares)
    {
        ArrayList<CareTeam> lista = new ArrayList<>();

        System.out.println("ENTRA EN FIREBASE");

        db.collection("footballer_careteam")
                .whereEqualTo("username_footballer", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            final ArrayList<String> careteams = new ArrayList<>();
                            careteams.add(Objects.requireNonNull(document.get("username_careteam")).toString());

                            System.out.println("CARETEAM: " + careteams);

                            db.collection("careteam")
                                    .whereArrayContains("username", Objects.requireNonNull(careteams))
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                System.out.println("HA PASAUUUUUU!!!");


                                                CareTeam ct = new CareTeam();

                                                ct.setUsername(document2.getString("username"));
                                                ct.setName(document2.getString("name"));
                                                ct.setTelcom(Integer.parseInt(Objects.requireNonNull(document2.getString("telecom"))));
                                                ct.setStatus(document2.getString("status"));
                                                ct.setNote(document2.getString("note"));

                                                favCareTeamName.setText(ct.getName());
                                                favCareTeamStatus.setText(ct.getStatus());
                                                favCareTeamTelecom.setText(String.valueOf(ct.getTelcom()));
                                                favCareTeamNote.setText(ct.getNote());

                                                lista.add(ct);
                                            }
                                        }
                                    });
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                uiFootballerFavsTeamCares,
                                android.R.layout.simple_spinner_dropdown_item,
                                uiFootballerFavsTeamCares.convert2Array(lista));

                        spinnerFavCareTeam.setAdapter(adapter);
                        uiFootballerFavsTeamCares.setFavsCareTeams(lista);
                    }
                });
    }

}