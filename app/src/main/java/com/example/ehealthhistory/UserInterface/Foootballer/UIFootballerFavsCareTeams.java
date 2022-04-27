package com.example.ehealthhistory.UserInterface.Foootballer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.UserInterface.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.Model.CareTeam.CareTeam;
import com.example.ehealthhistory.Database.FireBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class UIFootballerFavsCareTeams extends BaseActivity {

    private final FireBase fb = new FireBase();

    private final ArrayList<CareTeam> favsCareTeams = new ArrayList<>();
    private boolean flagFavsCT = false;
    private final ArrayList<CareTeam> noFavsCareTeams = new ArrayList<>();
    private boolean flagNoFavsCT = false;

    //IPFSController ipfsController = new IPFSController();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_favs_teamcares);

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Equipos Médicos de confianza");

        String username = getIntent().getStringExtra("username");

        final Spinner spinnerFavCareTeam = findViewById(R.id.spinnerFavCareTeams);

        final TextView favCareTeamName = findViewById(R.id.favCareTeamName);
        final TextView favCareTeamStatus = findViewById(R.id.favCareTeamStatus);
        final TextView favCareTeamTelecom = findViewById(R.id.favCareTeamTelecom);
        final TextView favCareTeamNote = findViewById(R.id.favCareTeamNote);

        final Spinner spinnerNewFavCareTeam = findViewById(R.id.spinnerNewFavCareTeams);

        final TextView newFavCareTeamName = findViewById(R.id.newFavCareTeamName);
        final TextView newFavCareTeamStatus = findViewById(R.id.newFavCareTeamStatus);
        final TextView newFavCareTeamTelecom = findViewById(R.id.newFavCareTeamTelecom);
        final TextView newFavCareTeamNote = findViewById(R.id.newFavCareTeamNote);

        final Button buttonAddNewFavCareTeam = findViewById(R.id.buttonAddNewFavCareTeam);

        // Establecer valores
        getValuesOfCareTeams(username, spinnerFavCareTeam, favCareTeamName, favCareTeamStatus,
                favCareTeamTelecom, favCareTeamNote,spinnerNewFavCareTeam, newFavCareTeamName, newFavCareTeamStatus,
                newFavCareTeamTelecom, newFavCareTeamNote);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFlagFavsCT()) {
                    representCareTeamData(spinnerFavCareTeam, favCareTeamName, favCareTeamStatus,
                             favCareTeamTelecom, favCareTeamNote, getFavsCareTeams());
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerNewFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFlagNoFavsCT()) {
                    representCareTeamData(spinnerNewFavCareTeam, newFavCareTeamName, newFavCareTeamStatus,
                             newFavCareTeamTelecom, newFavCareTeamNote, getNoFavsCareTeams());
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        /* Comprobar que no coinciden, añadir e irse. */
        buttonAddNewFavCareTeam.setOnClickListener((v -> {
            if(Objects.requireNonNull(findCareTeamInList(spinnerNewFavCareTeam, getNoFavsCareTeams())).getStatus().equals("activo")) {
                fb.addNewCareTeam2Footballer(username, findCareTeamInList(spinnerNewFavCareTeam, getNoFavsCareTeams()));
                Snackbar.make(findViewById(R.id.buttonAddNewFavCareTeam), R.string.success_adding_newcareteam, Snackbar.LENGTH_SHORT).show();

                //ipfsController.addToLog("El futblista " + username + "ha añadido el equipo médico de confianza: " +
                // findCareTeamInList(spinnerNewFavCareTeam, getNoFavsCareTeams())).getName());

                //ipfsController.saveText("Equipo médico confianza " + username);

                new Handler().postDelayed(
                        this::finish, 1000);
            }
            else
                Snackbar.make(findViewById(R.id.buttonAddNewFavCareTeam), R.string.error_adding_newcareteam, Snackbar.LENGTH_SHORT).show();

        }));
    }

    private void getValuesOfCareTeams(String username,
                                      Spinner spinnerFavCareTeam,
                                      TextView favCareTeamName, TextView favCareTeamStatus,
                                      TextView favCareTeamTelecom, TextView favCareTeamNote,
                                      Spinner spinnerNewFavCareTeam,
                                      TextView newFavCareTeamName, TextView newFavCareTeamStatus,
                                      TextView newFavCareTeamTelecom, TextView newFavCareTeamNote)
    {
        fb.getFootballerFavsCareTeams(username, spinnerFavCareTeam, favCareTeamName, favCareTeamStatus,
                favCareTeamTelecom, favCareTeamNote, this);

        fb.getFootballerNoFavsCareTeams(username, spinnerNewFavCareTeam, newFavCareTeamName, newFavCareTeamStatus,
                newFavCareTeamTelecom, newFavCareTeamNote, this);
    }

    private void representCareTeamData(Spinner selectedCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
                                       TextView newCareTeamTelecom, TextView newCareTeamNote, ArrayList<CareTeam> careteams)
    {
        CareTeam careTeamSelected = findCareTeamInList(selectedCareTeam, careteams);

        assert careTeamSelected != null;
        newCareTeamName.setText(careTeamSelected.getName());
        newCareTeamStatus.setText(careTeamSelected.getStatus());
        newCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelecom()));
        newCareTeamNote.setText(careTeamSelected.getNote());
    }


    private CareTeam findCareTeamInList(Spinner selectedCareTeam, ArrayList<CareTeam> careteams)
    {
        String idSelectedItem = selectedCareTeam.getSelectedItem().toString().split(". ")[0];

        for(CareTeam ct : careteams)
            if(idSelectedItem.equals(String.valueOf(ct.getId()))) return ct;
        return null;

    }

    public void representInitialSpinnerData(TextView favCareTeamName,
                                            TextView favCareTeamStatus, TextView favCareTeamTelecom, TextView favCareTeamNote) {
        CareTeam careTeamSelected = getFavsCareTeams().get(0);

        favCareTeamName.setText(careTeamSelected.getName());
        favCareTeamStatus.setText(careTeamSelected.getStatus());
        favCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelecom()));
        favCareTeamNote.setText(careTeamSelected.getNote());
    }

    public ArrayList<CareTeam> getFavsCareTeams() {
        return favsCareTeams;
    }

    public void addNoFavsCareTeams(CareTeam ct)
    {
        noFavsCareTeams.add(ct);
    }

    public ArrayList<CareTeam> getNoFavsCareTeams() {
        return noFavsCareTeams;
    }

    public boolean isFlagFavsCT() {
        return flagFavsCT;
    }

    public void setFlagFavsCT(boolean flagFavsCT) {
        this.flagFavsCT = flagFavsCT;
    }

    public boolean isFlagNoFavsCT() {
        return flagNoFavsCT;
    }

    public void setFlagNoFavsCT(boolean flagNoFavsCT) {
        this.flagNoFavsCT = flagNoFavsCT;
    }

    public void addToFavCareTeam(CareTeam ct)
    {
        if(ct!=null)
            favsCareTeams.add(ct);
    }

    public String[] convert2Array(ArrayList<CareTeam> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getId() + ". " +lista.get(i).getName();
        }

        return mStringArray;
    }


}
