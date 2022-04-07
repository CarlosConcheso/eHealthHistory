package com.example.ehealthhistory.database;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.example.ehealthhistory.database.dto.CareTeamDTO;
import com.example.ehealthhistory.database.dto.ClubDTO;
import com.example.ehealthhistory.database.dto.FootballerDTO;
import com.example.ehealthhistory.database.dto.HealthCareServiceDTO;
import com.example.ehealthhistory.database.dto.UserDTO;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareAvalibleTime;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import com.example.ehealthhistory.ui.CareTeam.MainCareTeam;
import com.example.ehealthhistory.ui.Club.MainClub;
import com.example.ehealthhistory.ui.Club.UIAddNewCareTeam;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.example.ehealthhistory.ui.Foootballer.UIFootballerFavsCareTeams;
import com.example.ehealthhistory.ui.Foootballer.UIFootballerHealthCares;
import com.example.ehealthhistory.ui.HealthCareService.MainHealthCareService;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FireBase {

    private final FirebaseFirestore db;

    public FireBase()
    {
        db = FirebaseFirestore.getInstance();
    }

    // ---------- Meodos pantalla inicial roles
    @SuppressLint("SetTextI18n")
    public void getNameFromUser(String username, TextView tv) {

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

    public void getRolesFromUsername(String username, Button botonFutbolista, Button botonMedico, Button botonClub) {

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

    public void fillSpinnerHealthcareFootballer(String username, Spinner spinner,
                                                UIFootballerHealthCares uiFootballerHealthCares,
                                                TextView healthCareCategory, TextView healthCareName,
                                                TextView healthCareCommentary, TextView healthCareAllDay,
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

    public void getFootballerFavsCareTeams(String username, Spinner spinnerFavCareTeam, TextView favCareTeamName,
                                           TextView favCareTeamStatus, TextView favCareTeamTelecom,
                                           TextView favCareTeamNote, UIFootballerFavsCareTeams uiFootballerFavsCareTeams)
    {
        ArrayList<CareTeam> careteams = new ArrayList<>();

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);

                                db.collection("careteam")
                                        .whereIn("username", footballerDTO.getCareteams())
                                        .get()
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                int id = 1;
                                                for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                    CareTeam ct = new CareTeam();
                                                    CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                    ct.setId(id);
                                                    ct.setUsername(careTeamDTO.getUsername());
                                                    ct.setName(careTeamDTO.getName());
                                                    ct.setTelcom(careTeamDTO.getTelecom());
                                                    ct.setStatus(careTeamDTO.getStatus());
                                                    ct.setNote(careTeamDTO.getNote());

                                                    favCareTeamName.setText(ct.getName());
                                                    favCareTeamStatus.setText(ct.getStatus());
                                                    favCareTeamTelecom.setText(String.valueOf(ct.getTelecom()));
                                                    favCareTeamNote.setText(ct.getNote());

                                                    uiFootballerFavsCareTeams.addToFavCareTeam(ct);
                                                    careteams.add(ct);

                                                    id++;
                                                }

                                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                            uiFootballerFavsCareTeams,
                                                            android.R.layout.simple_spinner_dropdown_item,
                                                            uiFootballerFavsCareTeams.convert2Array(careteams));
                                                    spinnerFavCareTeam.setAdapter(adapter);

                                                    uiFootballerFavsCareTeams.representInitialSpinnerData(favCareTeamName,
                                                            favCareTeamStatus, favCareTeamTelecom,
                                                            favCareTeamNote);

                                                    uiFootballerFavsCareTeams.setFlagFavsCT(true);
                                            }
                                        });
                        }
                    }
                });
    }
    
    public void getFootballerNoFavsCareTeams(String username, Spinner spinnerNewFavCareTeam, TextView newFavCareTeamName,
                                             TextView newFavCareTeamStatus, TextView newFavCareTeamTelecom,
                                             TextView newFavCareTeamNote, UIFootballerFavsCareTeams uiFootballerFavsCareTeams)
    {
        ArrayList<CareTeam> careteams = new ArrayList<>();

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);

                                db.collection("careteam")
                                        .whereNotIn("username", footballerDTO.getCareteams())
                                        .get()
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                int id = 1;
                                                for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                    CareTeam ct = new CareTeam();
                                                    CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                    ct.setId(id);
                                                    ct.setUsername(careTeamDTO.getUsername());
                                                    ct.setName(careTeamDTO.getName());
                                                    ct.setTelcom(careTeamDTO.getTelecom());
                                                    ct.setStatus(careTeamDTO.getStatus());
                                                    ct.setNote(careTeamDTO.getNote());

                                                    newFavCareTeamName.setText(ct.getName());
                                                    newFavCareTeamStatus.setText(ct.getStatus());
                                                    newFavCareTeamTelecom.setText(String.valueOf(ct.getTelecom()));
                                                    newFavCareTeamNote.setText(ct.getNote());

                                                    uiFootballerFavsCareTeams.addNoFavsCareTeams(ct);
                                                    careteams.add(ct);
                                                    id++;
                                                }

                                                if(careteams.size()>0) {
                                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                            uiFootballerFavsCareTeams,
                                                            android.R.layout.simple_spinner_dropdown_item,
                                                            uiFootballerFavsCareTeams.convert2Array(careteams));
                                                    spinnerNewFavCareTeam.setAdapter(adapter);

                                                    uiFootballerFavsCareTeams.representInitialSpinnerData(newFavCareTeamName,
                                                            newFavCareTeamStatus, newFavCareTeamTelecom,
                                                            newFavCareTeamNote);

                                                    uiFootballerFavsCareTeams.setFlagNoFavsCT(true);
                                                }
                                                else{
                                                    String [] noHealthCares = {"No hay medicos"};

                                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                            uiFootballerFavsCareTeams,
                                                            android.R.layout.simple_spinner_dropdown_item,
                                                            noHealthCares);
                                                    spinnerNewFavCareTeam.setAdapter(adapter);
                                                }
                                            }
                                        });
                        }
                    }
                });
    }

    public void addNewCareTeam2Footballer(String username, CareTeam careteam) {
        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);

                            ArrayList<String> auxFinalCareteams = new ArrayList<>(footballerDTO.getCareteams());
                            auxFinalCareteams.add(careteam.getUsername());

                            Map<String, Object> newCareteams = new HashMap<>();
                            newCareteams.put("careteams", auxFinalCareteams);
                            db.collection("footballer").document(document.getId()).update(newCareteams);
                        }
                    }
                });
    }

    public void representBasicDataAndClubsFootballer(String username, TextView nameActivityBase, TextView clubName,
                                                     TextView clubPresident, TextView clubAlias, TextView clubContact,
                                                     TextView clubTeamCare, MainClub mainClub)
    {

        ArrayList<Footballer> footballers = new ArrayList<>();

        db.collection("club")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            ClubDTO club = document.toObject(ClubDTO.class);

                            nameActivityBase.setText(club.getName());
                            clubName.setText(club.getName());
                            clubPresident.setText(club.getPresident());
                            clubAlias.setText(club.getAlias());
                            clubContact.setText(club.getContactname());

                            //Sacar nombre del equipo médico
                            db.collection("careteam")
                                    .whereEqualTo("username", club.getUsername_careteam())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                clubTeamCare.setText(careTeamDTO.getName());
                                            }
                                        }
                                    });

                            db.collection("footballer")
                                    .whereEqualTo("club", club.getName())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                Footballer fotballer = new Footballer();
                                                FootballerDTO footballerDTO = document2.toObject(FootballerDTO.class);

                                                fotballer.setName(footballerDTO.getName());
                                                fotballer.setActive(footballerDTO.isActive());

                                                footballers.add(fotballer);
                                            }

                                            mainClub.addFootballersRows(footballers);

                                        }
                                    });
                        }
                    }
                });
    }


    public void representClubCareTeamData(String username,
                                            TextView careTeamName, TextView careTeamStatus,
                                            TextView careTeamTelecom, TextView careTeamNote)
    {
        db.collection("club")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            ClubDTO clubDTO = document.toObject(ClubDTO.class);

                            String careteamusername = clubDTO.getUsername_careteam();

                            db.collection("careteam")
                                    .whereEqualTo("username", careteamusername)
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                careTeamName.setText(careTeamDTO.getName());
                                                careTeamStatus.setText(careTeamDTO.getStatus());
                                                careTeamTelecom.setText(String.valueOf(careTeamDTO.getTelecom()));
                                                careTeamNote.setText(careTeamDTO.getNote());
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void fillSpinnerNewClubCareTeam(String username, Spinner spinner, TextView newCareTeamName,
                                           TextView newCareTeamStatus, TextView newCareTeamTelecom,
                                           TextView newCareTeamNote,
                                           UIAddNewCareTeam uiAddNewCareTeam) {

        ArrayList<CareTeam> lista = new ArrayList<>();

        db.collection("club")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            ClubDTO clubDTO = document.toObject(ClubDTO.class);

                            db.collection("careteam")
                                    .whereNotEqualTo("username", clubDTO.getUsername_careteam())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                CareTeam ct = new CareTeam();
                                                CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                ct.setName(careTeamDTO.getName());
                                                ct.setStatus(careTeamDTO.getStatus());
                                                ct.setTelcom(careTeamDTO.getTelecom());
                                                ct.setNote(careTeamDTO.getNote());

                                                newCareTeamName.setText(careTeamDTO.getName());
                                                newCareTeamStatus.setText(careTeamDTO.getStatus());
                                                newCareTeamTelecom.setText(String.valueOf(careTeamDTO.getTelecom()));
                                                newCareTeamNote.setText(careTeamDTO.getNote());

                                                lista.add(ct);
                                                uiAddNewCareTeam.addNewCareTeam(ct);

                                            }

                                            uiAddNewCareTeam.setRestOfCareTeams(lista);

                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                    uiAddNewCareTeam,
                                                    android.R.layout.simple_spinner_dropdown_item,
                                                    uiAddNewCareTeam.convert2Array(lista));

                                            spinner.setAdapter(adapter);

                                            uiAddNewCareTeam.representInitialSpinnerData(
                                                    newCareTeamName,
                                                    newCareTeamStatus, newCareTeamTelecom,
                                                    newCareTeamNote);

                                            uiAddNewCareTeam.setConsulta(true);
                                        }
                                    });
                        }
                    }
                });
    }

    public void addNewCareTeam2Club(String usernameclub, String oldCareTeam, String newCareteam) {

        //buscar username por el nombre del club
        db.collection("careteam")
                .whereEqualTo("name", oldCareTeam)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            CareTeamDTO careTeamDTO = document.toObject(CareTeamDTO.class);

                            //Actualizar el username del careteam en Club.
                            db.collection("club")
                                    .whereEqualTo("username", usernameclub)
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {

                                                db.collection("careteam")
                                                        .whereEqualTo("name", newCareteam)
                                                        .get()
                                                        .addOnCompleteListener(task3 -> {
                                                            if (task3.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document3 : Objects.requireNonNull(task3.getResult())) {
                                                                    CareTeamDTO newCareTeamDTO = document3.toObject(CareTeamDTO.class);

                                                                    Map<String, Object> newCareteams = new HashMap<>();
                                                                    newCareteams.put("username_careteam", newCareTeamDTO.getUsername());

                                                                    db.collection("club").document(document2.getId()).update(newCareteams);

                                                                    //Actualizar el username del careteam en el array en Footballer.
                                                                    db.collection("footballer")
                                                                            .whereArrayContains("careteams", careTeamDTO.getUsername())
                                                                            .get()
                                                                            .addOnCompleteListener(task4 -> {
                                                                                if (task4.isSuccessful()) {
                                                                                    for (QueryDocumentSnapshot document4 : Objects.requireNonNull(task4.getResult())) {

                                                                                        FootballerDTO footballerDTO = document4.toObject(FootballerDTO.class);

                                                                                        // Recojo el contenido.
                                                                                        ArrayList<String> careteamsFootballer = footballerDTO.getCareteams();

                                                                                        // Ahora modifico de forma local los valores y se los paso
                                                                                        Map<String, Object> newValues = new HashMap<>();

                                                                                        System.out.println("----------------JUGADOR: " + footballerDTO.getName());

                                                                                        System.out.println("CARETEAMS ANTES: ");
                                                                                        for(int i=0; i<careteamsFootballer.size(); i++)
                                                                                                System.out.println("CARETEAM: " + careteamsFootballer.get(i));

                                                                                        ArrayList<String> auxFinalCareTeams = new ArrayList<>();

                                                                                        for(int i=0; i<careteamsFootballer.size(); i++) {
                                                                                            if(!careteamsFootballer.get(i).equals(newCareTeamDTO.getUsername())) {
                                                                                                if (careteamsFootballer.get(i).equals(careTeamDTO.getUsername())) {
                                                                                                    auxFinalCareTeams.add(newCareTeamDTO.getUsername());
                                                                                                }
                                                                                                else {
                                                                                                    auxFinalCareTeams.add(careteamsFootballer.get(i));
                                                                                                }
                                                                                            }


                                                                                        }

                                                                                        System.out.println("CARETEAMS DESPUÉS: ");
                                                                                        for(int i=0; i<auxFinalCareTeams.size(); i++)
                                                                                            System.out.println("CARETEAM: " + auxFinalCareTeams.get(i));


                                                                                        // Si no existe reemplaza el antiguo por el nuevo.
                                                                                        // Si existe, no hace nada mas que volver a pasarle los valores antiguos
                                                                                        newValues.put("careteams", auxFinalCareTeams);
                                                                                        db.collection("footballer").document(document4.getId()).update(newValues);
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
                    }
                });
    }

    public void representBasicDataAndCareTeamFootballer(String username, TextView nameActivityBase,
                                                        TextView careTeamName, TextView careTeamStatus,
                                                        TextView careTeamTelcom, TextView careTeamNote,
                                                        MainCareTeam mainCareTeam)
    {
        ArrayList<Footballer> footballers = new ArrayList<>();


        db.collection("careteam")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            CareTeamDTO careteamDTO = document.toObject(CareTeamDTO.class);

                            nameActivityBase.setText(careteamDTO.getName());
                            careTeamName.setText(careteamDTO.getName());
                            careTeamStatus.setText(careteamDTO.getStatus());
                            careTeamTelcom.setText(String.valueOf(careteamDTO.getTelecom()));
                            careTeamNote.setText(careteamDTO.getNote());

                            // Buscar futbolistas con ese careteam
                            db.collection("footballer")
                                    .whereArrayContains("careteams", careteamDTO.getUsername())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                Footballer footballer = new Footballer();
                                                FootballerContact fc = new FootballerContact();
                                                FootballerDTO footballerDTO = document2.toObject(FootballerDTO.class);

                                                footballer.setName(footballerDTO.getName());
                                                footballer.setActive(footballerDTO.isActive());
                                                fc.setTelecom(Integer.parseInt(footballerDTO.getContact_telecom()));

                                                footballer.setFootballerContact(fc);
                                                mainCareTeam.addFootballer(footballer);

                                                footballers.add(footballer);
                                                mainCareTeam.setConsulta(true);
                                            }
                                            mainCareTeam.addFootballersRows(footballers);
                                        }
                                    });
                        }
                    }
                });
    }


    public void representFootballersByCareTeam(String username, Spinner spinnerFootballers, MainHealthCareService mainHealthCareService)
    {
        ArrayList<Footballer> lista = new ArrayList<>();

        // Buscar futbolistas con ese careteam
        db.collection("footballer")
                .whereArrayContains("careteams", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int i = 1;
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Footballer footballer = new Footballer();
                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);

                            footballer.setId(i);
                            footballer.setName(footballerDTO.getName());
                            footballer.setUsername(footballerDTO.getUsername());
                            lista.add(footballer);

                            i++;
                        }

                        mainHealthCareService.setFootballers(lista);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                mainHealthCareService,
                                android.R.layout.simple_spinner_dropdown_item,
                                mainHealthCareService.convert2Array(lista));

                        spinnerFootballers.setAdapter(adapter);
                    }
                });
    }

    public void addHealthCareToFootballer(Footballer footballer,
                                          CheckBox activo, CheckBox checkBoxAllDay, Spinner healthCareCategory,
                                          EditText healthCareName,
                                          Spinner healthCareHoraInicio, Spinner healthCareHoraFin,
                                          Spinner healthCareMinsInicio, Spinner healthCareMinsFin,
                                          CheckBox checkBoxL, CheckBox checkBoxM, CheckBox checkBoxX,
                                          CheckBox checkBoxJ, CheckBox checkBoxV, CheckBox checkBoxS, CheckBox checkBoxD,
                                          EditText multiLineHealthCareCommentary, EditText multiLineHealthCareExtraDetails) {

        Map<String, Object> healthCareService = new HashMap<>();

        db.collection("healthcare")
                .whereEqualTo("username", footballer.getUsername())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        int numHealhcare = 1;

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            document.getId();
                            numHealhcare++;

                        }

                        healthCareService.put("active", activo.isChecked());
                        healthCareService.put("avalibleTime_allDay", checkBoxAllDay.isChecked());

                        // Si spinner habilitado, eso significa que las horas hay que establecerlas,
                        // si no está activado, poner vacio.
                        if(healthCareHoraInicio.isEnabled() && healthCareHoraFin.isEnabled() &&
                                healthCareMinsInicio.isEnabled() && healthCareMinsFin.isEnabled()) {
                            healthCareService.put("avalibleTime_endTime", healthCareHoraInicio.getSelectedItem().toString() + ":" + healthCareMinsInicio.getSelectedItem().toString());
                            healthCareService.put("avalibleTime_startTime", healthCareHoraFin.getSelectedItem().toString() + ":" + healthCareMinsFin.getSelectedItem().toString());
                        }
                        else
                        {
                            healthCareService.put("avalibleTime_endTime", "");
                            healthCareService.put("avalibleTime_startTime", "");
                        }

                        healthCareService.put("category", healthCareCategory.getSelectedItem().toString());
                        healthCareService.put("commentary", multiLineHealthCareCommentary.getText().toString());
                        healthCareService.put("extraDetails", multiLineHealthCareExtraDetails.getText().toString());
                        healthCareService.put("name", healthCareName.getText().toString());
                        healthCareService.put("username", footballer.getUsername());

                        //daysOfWeek : { MON, TUE, WED, THU, FRI, SAT, SUN}
                        ArrayList<String> avalibleTime_daysOfHealthCare = new ArrayList<>();
                        if(checkBoxL.isChecked())
                            avalibleTime_daysOfHealthCare.add("MON");
                        if(checkBoxM.isChecked())
                            avalibleTime_daysOfHealthCare.add("TUE");
                        if(checkBoxX.isChecked())
                            avalibleTime_daysOfHealthCare.add("WED");
                        if(checkBoxJ.isChecked())
                            avalibleTime_daysOfHealthCare.add("THU");
                        if(checkBoxV.isChecked())
                            avalibleTime_daysOfHealthCare.add("FRI");
                        if(checkBoxS.isChecked())
                            avalibleTime_daysOfHealthCare.add("SAT");
                        if(checkBoxD.isChecked())
                            avalibleTime_daysOfHealthCare.add("SUN");

                        healthCareService.put("avalibleTime_daysOfHealthCare", avalibleTime_daysOfHealthCare);

                        String idDocument = healthCareCategory.getSelectedItem().toString() + " " + footballer.getName() + " " + numHealhcare;
                        System.out.println("ID DOCUMENTO: " + idDocument);

                        db.collection("healthcare").document(idDocument).set(healthCareService);

                    }
                });
    }

}