package com.example.ehealthhistory.ui.Club;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UIAddNewCareTeam extends BaseActivity {

    private ModelFactory mf = new ModelFactory();
    private CareTeam actualCareTeam = mf.getCareTeamROV();

    private ArrayList<CareTeam> careTeams = mf.getCareTeams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_add_newcareteam);

        Bundle bundle = new Bundle();
        bundle.putString("Add Care Team", "New CareTeam's Menu");

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Establecer equipo médico");

        // Encontramos todos los valores de la pantalla
        final TextView careTeamName = (TextView) findViewById(R.id.careTeamName);
        final TextView careTeamStatus = (TextView) findViewById(R.id.careTeamStatus);
        final TextView careTeamTelecom = (TextView) findViewById(R.id.careTeamTelecom);
        final TextView careTeamNote = (TextView) findViewById(R.id.careTeamNote);

        final Spinner spinnerCareTeams = (Spinner) findViewById(R.id.spinnerCareTeams);

        final TextView newCareTeamName = (TextView) findViewById(R.id.newCareTeamName);
        final TextView newCareTeamStatus = (TextView) findViewById(R.id.newCareTeamStatus);
        final TextView newCareTeamTelecom = (TextView) findViewById(R.id.newCareTeamTelecom);
        final TextView newCareTeamNote = (TextView) findViewById(R.id.newCareTeamNote);

        final Button buttonAddNewCareTeam = (Button) findViewById(R.id.buttonAddNewCareTeam);

        // Establecer valores
        careTeamName.setText(actualCareTeam.getName());
        careTeamStatus.setText(actualCareTeam.getStatus().toString());
        careTeamTelecom.setText(String.valueOf(actualCareTeam.getTelcom()));
        careTeamNote.setText(actualCareTeam.getNote());

        establecerNewTeamCares(spinnerCareTeams);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerCareTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addNewTeamCareData(spinnerCareTeams.getSelectedItem().toString(), newCareTeamName, newCareTeamStatus,
                        newCareTeamTelecom, newCareTeamNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) { return; } });

        //Añadir el nuevo equipo médico
        buttonAddNewCareTeam.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(careTeamName.getText().toString() != spinnerCareTeams.getSelectedItem().toString()) {
                    changeTo(v.getContext(), MainClub.class);
                }
                else
                    Snackbar.make(findViewById(R.id.buttonAddNewCareTeam), R.string.error_usuario_NewActualCareTeam, Snackbar.LENGTH_SHORT).show();

            }
        }));
    }

    private void addNewTeamCareData(String nameSelectedCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
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

    // ---------------------------------------------------------------------------------------------
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

    private static void changeTo(Context mContext, Class clase) {
        Intent intent = new Intent(mContext, clase);
        mContext.startActivity(intent);
    }
}