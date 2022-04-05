package com.example.ehealthhistory.ui.Club;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.database.FireBase;

import java.util.ArrayList;

public class UIAddNewCareTeam extends BaseActivity {

    private final FireBase fb = new FireBase();
    private ArrayList<CareTeam> restOfCareTeams = new ArrayList<>();
    private boolean consulta = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_add_newcareteam);
        String username = getIntent().getStringExtra("username");

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Establecer nuevo Médico");

        // Encontramos todos los valores de la pantalla
        final TextView careTeamName = findViewById(R.id.careTeamName);
        final TextView careTeamStatus = findViewById(R.id.careTeamStatus);
        final TextView careTeamTelecom = findViewById(R.id.careTeamTelecom);
        final TextView careTeamNote = findViewById(R.id.careTeamNote);

        final Spinner spinnerCareTeams = findViewById(R.id.spinnerCareTeams);

        final TextView newCareTeamName = findViewById(R.id.newCareTeamName);
        final TextView newCareTeamStatus = findViewById(R.id.newCareTeamStatus);
        final TextView newCareTeamTelecom = findViewById(R.id.newCareTeamTelecom);
        final TextView newCareTeamNote = findViewById(R.id.newCareTeamNote);

        final Button buttonAddNewCareTeam = findViewById(R.id.buttonAddNewCareTeam);

        // Establecer valores
        fb.representClubCareTeamData(username,careTeamName,careTeamStatus,careTeamTelecom,careTeamNote);
        fb.fillSpinnerNewClubCareTeam(username, spinnerCareTeams, newCareTeamName,
                newCareTeamStatus, newCareTeamTelecom, newCareTeamNote, this);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerCareTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isConsulta())
                addNewTeamCareData(spinnerCareTeams.getSelectedItem().toString(), newCareTeamName, newCareTeamStatus,
                        newCareTeamTelecom, newCareTeamNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        //Añadir el nuevo equipo médico
        buttonAddNewCareTeam.setOnClickListener((v -> {
                fb.addNewCareTeam2Club(username, careTeamName.getText().toString(),spinnerCareTeams.getSelectedItem().toString());
                finish();
        }));
    }

    private void addNewTeamCareData(String nameSelectedCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
                                    TextView newCareTeamTelecom, TextView newCareTeamNote)
    {
        CareTeam careTeamSelected = findCareTeam(nameSelectedCareTeam);
        newCareTeamName.setText(careTeamSelected.getName());
        newCareTeamStatus.setText(careTeamSelected.getStatus());
        newCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelecom()));
        newCareTeamNote.setText(careTeamSelected.getNote());
    }

    private CareTeam findCareTeam(String name)
    {
        CareTeam careTeamSelected = null;
        for(CareTeam c : restOfCareTeams) {
            if (c.getName().equals(name)) {
                careTeamSelected = c;
            }
        }
        return careTeamSelected;

    }

    public ArrayList<CareTeam> getRestOfCareTeams() {
        return restOfCareTeams;
    }

    public void setRestOfCareTeams(ArrayList<CareTeam> careTeams) {
        this.restOfCareTeams = careTeams;
    }

    public void addNewCareTeam(CareTeam ct)
    {
        getRestOfCareTeams().add(ct);
    }

    public boolean isConsulta() {
        return consulta;
    }

    public void setConsulta(boolean consulta) {
        this.consulta = consulta;
    }

    public String[] convert2Array(ArrayList<CareTeam> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getName();
        }

        return mStringArray;
    }

    public void representInitialSpinnerData(TextView newCareTeamName,
                                            TextView newCareTeamStatus, TextView newCareTeamTelecom,
                                            TextView newCareTeamNote) {
        CareTeam ct = getRestOfCareTeams().get(0);

        newCareTeamName.setText(ct.getName());
        newCareTeamStatus.setText(ct.getStatus());
        newCareTeamTelecom.setText(String.valueOf(ct.getTelecom()));
        newCareTeamNote.setText(ct.getNote());
    }
}