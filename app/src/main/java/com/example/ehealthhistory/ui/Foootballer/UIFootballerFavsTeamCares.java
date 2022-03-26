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

public class UIFootballerFavsTeamCares extends BaseActivity {

    private final FireBase fb = new FireBase();

    private ArrayList<CareTeam> favsCareTeams = new ArrayList<>();
    private ArrayList<CareTeam> noFavsCareTeams = new ArrayList<>();

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

        establecerNewTeamCares(spinnerFavCareTeam);
        establecerNewTeamCares(spinnerNewFavCareTeam);

        // Establecer valores
        fb.representarFootballerFavsCareTeams(username, spinnerFavCareTeam, favCareTeamName, favCareTeamStatus,
                favCareTeamTelecom, favCareTeamNote, this);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addTeamCareData(spinnerFavCareTeam.getSelectedItem().toString(), favCareTeamName, favCareTeamStatus,
                        favCareTeamTelecom, favCareTeamNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerNewFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addTeamCareData(spinnerNewFavCareTeam.getSelectedItem().toString(), newFavCareTeamName, newFavCareTeamStatus,
                        newFavCareTeamTelecom, newFavCareTeamNote);
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

    private void addTeamCareData(String nameSelectedCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
                                    TextView newCareTeamTelecom, TextView newCareTeamNote)
    {
        CareTeam careTeamSelected = findCareTeam(nameSelectedCareTeam);
        newCareTeamName.setText(careTeamSelected.getName());
        newCareTeamStatus.setText(careTeamSelected.getStatus());
        newCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelcom()));
        newCareTeamNote.setText(careTeamSelected.getNote());
    }


    private CareTeam findCareTeam(String name)
    {
        CareTeam careTeamSelected = null;
        for(CareTeam c : careTeams) {
            if (c.getName().equals(name)) {
                careTeamSelected = c;
            }
        }
        return careTeamSelected;

    }

    public ArrayList<CareTeam> getFavsCareTeams() {
        return favsCareTeams;
    }

    public void setFavsCareTeams(ArrayList<CareTeam> favsCareTeams) {
        this.favsCareTeams = favsCareTeams;
    }

    public ArrayList<CareTeam> getNoFavsCareTeams() {
        return noFavsCareTeams;
    }

    public void setNoFavsCareTeams(ArrayList<CareTeam> noFavsCareTeams) {
        this.noFavsCareTeams = noFavsCareTeams;
    }

    // Establecer valor spinner Team Cares
    private void establecerNewTeamCares(Spinner spinnerCareTeams) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                convert2Array(careTeams));

        spinnerCareTeams.setAdapter(adapter);
    }

    public String[] convert2Array(ArrayList<CareTeam> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getName();
        }

        return mStringArray;
    }
}
