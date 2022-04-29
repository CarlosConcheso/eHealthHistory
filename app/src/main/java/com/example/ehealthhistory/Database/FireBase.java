package com.example.ehealthhistory.Database;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.data.Model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.Model.Club.Club;
import com.example.ehealthhistory.data.Model.footballer.Footballer;
import com.example.ehealthhistory.Database.DTO.CareTeamDTO;
import com.example.ehealthhistory.Database.DTO.ClubDTO;
import com.example.ehealthhistory.Database.DTO.FootballerDTO;
import com.example.ehealthhistory.Database.DTO.HealthCareServiceDTO;
import com.example.ehealthhistory.Database.DTO.UserDTO;
import com.example.ehealthhistory.data.Model.healthCareService.HealthCareAvalibleTime;
import com.example.ehealthhistory.data.Model.healthCareService.HealthCareService;
import com.example.ehealthhistory.UserInterface.CareTeam.MainCareTeam;
import com.example.ehealthhistory.UserInterface.Club.MainClub;
import com.example.ehealthhistory.UserInterface.Club.UIAddNewCareTeam;
import com.example.ehealthhistory.UserInterface.Foootballer.MainFootballer;
import com.example.ehealthhistory.UserInterface.Foootballer.UIFootballerFavsCareTeams;
import com.example.ehealthhistory.UserInterface.Foootballer.UIFootballerHealthCares;
import com.example.ehealthhistory.UserInterface.HealthCareService.MainHealthCareService;
import com.example.ehealthhistory.UserInterface.MainRoles;
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
    public void getNameFromUser(String username, TextView tv, MainRoles mainRoles) {

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            UserDTO userDTO = document.toObject(UserDTO.class);
                            mainRoles.setName(userDTO.getName());

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
                                             TextView footballerDNI, TextView footballerBirthDay,
                                             TextView footballerTelcom, MainFootballer mainFootballer) {

        String nodata = "-";

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);
                            Footballer footballer = new Footballer();

                            footballer.setDNI(footballerDTO.getDni());
                            footballer.setName(footballerDTO.getName());
                            footballer.setBirthday(footballerDTO.getBirthday());

                            if(footballerDTO.getTelecom() != null) {
                                footballer.setTelecom(Integer.parseInt(footballerDTO.getTelecom()));
                                footballerTelcom.setText(String.valueOf(footballer.getTelecom()));
                            }
                            else
                                footballerTelcom.setText(nodata);

                            footballerDNI.setText(footballer.getDNI());

                            if(footballer.getBirthday()!= null)
                                footballerBirthDay.setText(footballer.getBirthday());
                            else
                                footballerBirthDay.setText(nodata);

                            mainFootballer.setFootballer(footballer);
                        }
                    }
                });
    }

    public void representBasicFootballerHealthCares(String username, MainFootballer mainFootballer)
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
                        mainFootballer.setHealthCaresServices(healthcares);
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

        String nodata = "-";

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            FootballerDTO footballerDTO = document.toObject(FootballerDTO.class);

                            if(footballerDTO.getContact_name() != null) {
                                footballerContactName.setText(footballerDTO.getContact_name());
                            }
                            else
                                footballerContactName.setText(nodata);

                            if(footballerDTO.getContact_telecom() != null) {
                                footballerContactTelf.setText(footballerDTO.getContact_telecom());
                            }
                            else
                                footballerContactTelf.setText(nodata);

                            if(footballerDTO.getComunication_lenguage() != null) {
                                footballerContactLenguaje.setText(footballerDTO.getComunication_lenguage());
                            }
                            else
                                footballerContactLenguaje.setText(nodata);

                            if(footballerDTO.getComunication_lenguage() != null) {
                                footballerContactAdress.setText(footballerDTO.getContact_address());
                            }
                            else
                                footballerContactAdress.setText(nodata);

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

    public void fillSpinnerHealthCareFootballer(String username, Spinner spinner,
                                                UIFootballerHealthCares uiFootballerHealthCares,
                                                TextView healthCareCategory, TextView healthCareName,
                                                TextView healthCareCommentary, TextView healthCareAllDay,
                                                TextView healthCareHoraInicio, TextView healthCareHoraFin,
                                                TextView healthCareDays, TextView healthCareNote,
                                                TextView healthCareCareTeam, TextView healthCareDay) {

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
                            hc.setCareTeam(healthCareServiceDTO.getCareteam_name());
                            hc.setDay(healthCareServiceDTO.getHealthcare_day());

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

                            uiFootballerHealthCares.representInitialSpinnerData(
                                    healthCareCategory, healthCareName,
                                    healthCareCommentary, healthCareAllDay,
                                    healthCareHoraInicio, healthCareHoraFin,
                                    healthCareDays, healthCareNote,
                                    healthCareCareTeam, healthCareDay);

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

    @SuppressLint("SetTextI18n")
    public void representBasicClubDataAndFootballer(String username, TextView nameActivityBase, TextView clubCIF,
                                                    TextView clubPresident, TextView clubAlias, TextView clubContact,
                                                    TextView clubActive, TextView clubTeamCare, MainClub mainClub)
    {
        ArrayList<Footballer> footballers = new ArrayList<>();

        db.collection("club")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            ClubDTO clubDTO = document.toObject(ClubDTO.class);
                            Club club = new Club();

                            club.setCIF(clubDTO.getCif());
                            club.setName(clubDTO.getName());
                            club.setPresident(clubDTO.getPresident());
                            club.setAlias(clubDTO.getAlias());
                            club.setContactName(clubDTO.getContactname());
                            club.setActive(clubDTO.isActive());

                            nameActivityBase.setText(club.getName());

                            clubCIF.setText(club.getCIF());
                            clubPresident.setText(club.getPresident());
                            clubAlias.setText(club.getAlias());
                            if(club.isActive())
                                clubActive.setText("Si");
                            else
                                clubActive.setText("No");

                            clubContact.setText(club.getContactName());

                            mainClub.setClub(club);

                            //Sacar nombre del equipo médico
                            db.collection("careteam")
                                    .whereEqualTo("username", clubDTO.getUsername_careteam())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                CareTeam careteam = new CareTeam();
                                                careteam.setName(careTeamDTO.getName());
                                                careteam.setUsername(careTeamDTO.getUsername());
                                                careteam.setStatus(careTeamDTO.getStatus());
                                                careteam.setNote(careTeamDTO.getNote());
                                                careteam.setTelcom(careTeamDTO.getTelecom());

                                                club.setClubCareTeam(careteam);
                                                clubTeamCare.setText(club.getClubCareTeam().getName());

                                                db.collection("footballer")
                                                        .whereEqualTo("club", club.getName())
                                                        .get()
                                                        .addOnCompleteListener(task3 -> {
                                                            if (task3.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document3 : Objects.requireNonNull(task3.getResult())) {

                                                                    Footballer fotballer = new Footballer();
                                                                    FootballerDTO footballerDTO = document3.toObject(FootballerDTO.class);

                                                                    fotballer.setName(footballerDTO.getName());
                                                                    fotballer.setActive(footballerDTO.isActive());

                                                                    footballers.add(fotballer);
                                                                    mainClub.setFootballers(footballers);
                                                                }

                                                                mainClub.getClub().setFootballers(footballers);
                                                                mainClub.addFootballersRows(footballers);
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void refreshClubCareTeam(String username, TextView careteamName)
    {
        db.collection("club")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            ClubDTO clubDTO = document.toObject(ClubDTO.class);
                            Club club = new Club();

                            club.setName(clubDTO.getName());
                            club.setPresident(clubDTO.getPresident());
                            club.setAlias(clubDTO.getAlias());
                            club.setContactName(clubDTO.getContactname());
                            club.setActive(clubDTO.isActive());

                            //Sacar nombre del equipo médico
                            db.collection("careteam")
                                    .whereEqualTo("username", clubDTO.getUsername_careteam())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                CareTeam careteam = new CareTeam();
                                                careteam.setName(careTeamDTO.getName());
                                                careteam.setUsername(careTeamDTO.getUsername());
                                                careteam.setStatus(careTeamDTO.getStatus());
                                                careteam.setNote(careTeamDTO.getNote());
                                                careteam.setTelcom(careTeamDTO.getTelecom());

                                                club.setClubCareTeam(careteam);
                                                careteamName.setText(club.getClubCareTeam().getName());

                                            }
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

    public void fillSpinnerNewCareTeams(String username, Spinner spinner, TextView newCareTeamName,
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
                                            int i=1;
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                CareTeam ct = new CareTeam();
                                                CareTeamDTO careTeamDTO = document2.toObject(CareTeamDTO.class);

                                                ct.setId(i);
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
                                                i++;
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

    public void addNewCareTeam2Club(String usernameclub, String oldCareTeam, CareTeam newCT) {

        String newCareteam = newCT.getName();

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

    public void representCareTeamBasicDataAndFootballers(String username,
                                                         TextView careTeamCIF, TextView careTeamStatus,
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
                            CareTeam ct = mainCareTeam.getCareTeam();

                            ct.setCIF(careteamDTO.getCif());
                            ct.setName(careteamDTO.getName());
                            ct.setTelcom(careteamDTO.getTelecom());
                            ct.setNote(careteamDTO.getNote());
                            ct.setStatus(careteamDTO.getStatus());

                            careTeamCIF.setText(ct.getCIF());
                            careTeamStatus.setText(ct.getStatus());
                            careTeamTelcom.setText(String.valueOf(ct.getTelecom()));
                            careTeamNote.setText(ct.getNote());

                            // Buscar futbolistas con ese careteam
                            db.collection("footballer")
                                    .whereArrayContains("careteams", careteamDTO.getUsername())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : Objects.requireNonNull(task2.getResult())) {
                                                Footballer footballer = new Footballer();
                                                Club clubF = new Club();
                                                FootballerDTO footballerDTO = document2.toObject(FootballerDTO.class);

                                                footballer.setName(footballerDTO.getName());
                                                footballer.setActive(footballerDTO.isActive());
                                                clubF.setName(footballerDTO.getClub());

                                                footballer.setClub(clubF);

                                                footballers.add(footballer);
                                            }

                                            mainCareTeam.setFootballers(footballers);
                                            mainCareTeam.addFootballersRows(mainCareTeam.getFootballers());
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


    public void addHealthCare2Footballer(Footballer footballer,
                                         CheckBox activo, CheckBox checkBoxAllDay, Spinner healthCareCategory,
                                         EditText healthCareName,
                                         Spinner healthCareHoraInicio, Spinner healthCareHoraFin,
                                         Spinner healthCareMinsInicio, Spinner healthCareMinsFin,
                                         CheckBox checkBoxL, CheckBox checkBoxM, CheckBox checkBoxX,
                                         CheckBox checkBoxJ, CheckBox checkBoxV, CheckBox checkBoxS, CheckBox checkBoxD,
                                         EditText multiLineHealthCareCommentary, EditText multiLineHealthCareExtraDetails,
                                         String careteamname, String dayOfHealthCare) {

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
                            healthCareService.put("avalibleTime_startTime", healthCareHoraInicio.getSelectedItem().toString() + ":" + healthCareMinsInicio.getSelectedItem().toString());
                            healthCareService.put("avalibleTime_endTime", healthCareHoraFin.getSelectedItem().toString() + ":" + healthCareMinsFin.getSelectedItem().toString());
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
                        healthCareService.put("careteam_name", careteamname);
                        healthCareService.put("healthcare_day", dayOfHealthCare);


                        String idDocument = healthCareCategory.getSelectedItem().toString() + " " + footballer.getName() + " " + numHealhcare;

                        db.collection("healthcare").document(idDocument).set(healthCareService);
                    }
                });
    }
}