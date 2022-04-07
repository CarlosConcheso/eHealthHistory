package com.example.ehealthhistory.ui.Foootballer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.database.FireBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UIFootballerFavsCareTeams extends BaseActivity {

    private final FireBase fb = new FireBase();

    private ArrayList<CareTeam> favsCareTeams = new ArrayList<>();
    private boolean flagFavsCT = false;
    private ArrayList<CareTeam> noFavsCareTeams = new ArrayList<>();
    private boolean flagNoFavsCT = false;

    private final ModelFactory mf = new ModelFactory();
    private final ArrayList<CareTeam> careTeams = mf.getCareTeams();

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
        fb.getFootballerFavsCareTeams(username, spinnerFavCareTeam, favCareTeamName, favCareTeamStatus,
                favCareTeamTelecom, favCareTeamNote, this);
        fb.getFootballerNoFavsCareTeams(username, spinnerNewFavCareTeam, newFavCareTeamName, newFavCareTeamStatus,
                newFavCareTeamTelecom, newFavCareTeamNote, this);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFlagFavsCT()) {
                    representTeamCareData(spinnerFavCareTeam, favCareTeamName, favCareTeamStatus,
                             favCareTeamTelecom, favCareTeamNote, getFavsCareTeams());
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerNewFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFlagNoFavsCT()) {
                    representTeamCareData(spinnerNewFavCareTeam, newFavCareTeamName, newFavCareTeamStatus,
                             newFavCareTeamTelecom, newFavCareTeamNote, getNoFavsCareTeams());
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        /* Comprobar que no coinciden, añadir e irse. */
        buttonAddNewFavCareTeam.setOnClickListener((v -> {
            if(spinnerNewFavCareTeam.getSelectedItem().toString().equals(spinnerFavCareTeam.getSelectedItem().toString()))
                finish();
            else
                Snackbar.make(findViewById(R.id.buttonAddNewFavCareTeam), R.string.error_usuario_newfavteamcare, Snackbar.LENGTH_SHORT).show();
        }));
    }

    private void representTeamCareData(Spinner selectedCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
                                       TextView newCareTeamTelecom, TextView newCareTeamNote, ArrayList<CareTeam> careteams)
    {
        CareTeam careTeamSelected = findCareTeamInList(selectedCareTeam, careteams);

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

    public void setFavsCareTeams(ArrayList<CareTeam> favsCareTeams) {
        this.favsCareTeams = favsCareTeams;
    }

    public void addNoFavsCareTeams(CareTeam ct)
    {
        noFavsCareTeams.add(ct);
    }

    public ArrayList<CareTeam> getNoFavsCareTeams() {
        return noFavsCareTeams;
    }

    public void setNoFavsCareTeams(ArrayList<CareTeam> noFavsCareTeams) {
        this.noFavsCareTeams = noFavsCareTeams;
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

    // Establecer valor spinner Team Cares
    private void establecerNewTeamCares(Spinner spinnerCareTeams, ArrayList<CareTeam> careteamsPam) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                convert2Array(careteamsPam));

        spinnerCareTeams.setAdapter(adapter);
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
