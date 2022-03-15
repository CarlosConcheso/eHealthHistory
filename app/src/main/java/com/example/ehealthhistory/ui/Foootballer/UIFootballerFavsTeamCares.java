package com.example.ehealthhistory.ui.Foootballer;

import android.content.Context;
import android.content.Intent;
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
import com.example.ehealthhistory.ui.HealthCareService.MainHealthCareService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UIFootballerFavsTeamCares extends BaseActivity {


    private ModelFactory mf = new ModelFactory();
    private CareTeam actualCareTeam = mf.getCareTeams().get(0);

    private ArrayList<CareTeam> careTeams = mf.getCareTeams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_favs_teamcares);
        Bundle bundle = new Bundle();
        bundle.putString("Footballer's Favs Team Cares", "Footballer's Favs Team Cares");

        final TextView nameActivityBase = (TextView) findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Equipos Médicos de confianza");

        final Spinner spinnerFavCareTeam = (Spinner) findViewById(R.id.spinnerFavCareTeams);

        final TextView favCareTeamName = (TextView) findViewById(R.id.favCareTeamName);
        final TextView favCareTeamStatus = (TextView) findViewById(R.id.favCareTeamStatus);
        final TextView favCareTeamTelecom = (TextView) findViewById(R.id.favCareTeamTelecom);
        final TextView favCareTeamNote = (TextView) findViewById(R.id.favCareTeamNote);

        final Spinner spinnerNewFavCareTeam = (Spinner) findViewById(R.id.spinnerNewFavCareTeams);

        final TextView newFavCareTeamName = (TextView) findViewById(R.id.newFavCareTeamName);
        final TextView newFavCareTeamStatus = (TextView) findViewById(R.id.newFavCareTeamStatus);
        final TextView newFavCareTeamTelecom = (TextView) findViewById(R.id.newFavCareTeamTelecom);
        final TextView newFavCareTeamNote = (TextView) findViewById(R.id.newFavCareTeamNote);

        final Button buttonAddNewFavCareTeam = (Button) findViewById(R.id.buttonAddNewFavCareTeam);

        establecerNewTeamCares(spinnerFavCareTeam);
        establecerNewTeamCares(spinnerNewFavCareTeam);

        // Establecer valores
        favCareTeamName.setText(actualCareTeam.getName());
        favCareTeamStatus.setText(actualCareTeam.getStatus().toString());
        favCareTeamTelecom.setText(String.valueOf(actualCareTeam.getTelcom()));
        favCareTeamNote.setText(actualCareTeam.getNote());

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addTeamCareData(spinnerFavCareTeam.getSelectedItem().toString(), favCareTeamName, favCareTeamStatus,
                        favCareTeamTelecom, favCareTeamNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) { return; } });

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerNewFavCareTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addTeamCareData(spinnerNewFavCareTeam.getSelectedItem().toString(), newFavCareTeamName, newFavCareTeamStatus,
                        newFavCareTeamTelecom, newFavCareTeamNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) { return; } });

        //Comprobar que no coinciden, añadir e irse.
        buttonAddNewFavCareTeam.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerNewFavCareTeam.getSelectedItem().toString() != spinnerFavCareTeam.getSelectedItem().toString())
                    //changeTo(v.getContext(), MainFootballer.class);
                    finish();
                else
                    Snackbar.make(findViewById(R.id.buttonAddNewFavCareTeam), R.string.error_usuario_newfavteamcare, Snackbar.LENGTH_SHORT).show();
            }

        }));
    }

    private void addTeamCareData(String nameSelectedCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
                                    TextView newCareTeamTelecom, TextView newCareTeamNote)
    {
        CareTeam careTeamSelected = findCareTeam(nameSelectedCareTeam);
        newCareTeamName.setText(careTeamSelected.getName());
        newCareTeamStatus.setText(careTeamSelected.getStatus().toString());
        newCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelcom()));
        newCareTeamNote.setText(careTeamSelected.getNote());
    }


    private CareTeam findCareTeam(String name)
    {
        CareTeam careTeamSelected = null;
        for(CareTeam c : careTeams) {
            if (c.getName() == name) {
                careTeamSelected = c;
            }
        }
        return careTeamSelected;

    }

    // Establecer valor spinner Team Cares
    private void establecerNewTeamCares(Spinner spinnerCareTeams) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                convert2Array(careTeams));

        spinnerCareTeams.setAdapter(adapter);
    }

    private String[] convert2Array(ArrayList<CareTeam> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getName();
        }

        return mStringArray;
    }
}
