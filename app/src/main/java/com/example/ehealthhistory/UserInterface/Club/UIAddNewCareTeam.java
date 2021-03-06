package com.example.ehealthhistory.UserInterface.Club;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.IPFS.IPFSController;
import com.example.ehealthhistory.UserInterface.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.Model.CareTeam.CareTeam;
import com.example.ehealthhistory.Database.FireBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class UIAddNewCareTeam extends BaseActivity {

    private final FireBase fb = new FireBase();
    private ArrayList<CareTeam> restOfCareTeams = new ArrayList<>();
    private boolean consulta = false;

    IPFSController ipfsController = new IPFSController();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_add_newcareteam);
        String username = getIntent().getStringExtra("username");

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Establecer nuevo Médico");

        // Encontramos todos los valores de la pantalla
        final TextView careTeamCIF = findViewById(R.id.careTeamCIF);
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
        fb.representClubCareTeamData(username,careTeamCIF,careTeamName,careTeamStatus,careTeamTelecom,careTeamNote);
        fb.fillSpinnerNewCareTeams(username, spinnerCareTeams, newCareTeamName,
                newCareTeamStatus, newCareTeamTelecom, newCareTeamNote, this);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerCareTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isConsulta())
                addNewCareTeamData(spinnerCareTeams, newCareTeamName, newCareTeamStatus,
                        newCareTeamTelecom, newCareTeamNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });

        //Añadir el nuevo equipo médico
        buttonAddNewCareTeam.setOnClickListener(v -> {

            CareTeam ct = findCareTeam(spinnerCareTeams);

            assert ct != null;
            if(ct.getStatus().equals("activo"))
                {
                    fb.addNewCareTeam2Club(username, careTeamName.getText().toString(), ct);
                    Snackbar.make(findViewById(R.id.buttonAddNewCareTeam), R.string.success_adding_newcareteam, Snackbar.LENGTH_SHORT).show();

                    ipfsController.addToLog("El club " + username + " ha sustituido el anterior equipo médico: " +
                    careTeamName.getText().toString() + ", por: " + ct.getName());

                    ipfsController.saveText(username + ": AddNewCareTeam");

                    new Handler().postDelayed(
                            this::finish, 1000);
                }
                else
                    Snackbar.make(findViewById(R.id.buttonAddNewCareTeam), R.string.error_adding_newcareteam, Snackbar.LENGTH_SHORT).show();
        });
    }

    private void addNewCareTeamData(Spinner spinnerCareTeam, TextView newCareTeamName, TextView newCareTeamStatus,
                                    TextView newCareTeamTelecom, TextView newCareTeamNote)
    {
        String nodata = "-";

        CareTeam careTeamSelected = findCareTeam(spinnerCareTeam);
        newCareTeamName.setText(Objects.requireNonNull(careTeamSelected).getName());
        newCareTeamStatus.setText(careTeamSelected.getStatus());

        if(careTeamSelected.getTelecom() != 0)
            newCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelecom()));
        else
            newCareTeamTelecom.setText(nodata);

        if(careTeamSelected.getNote() != null)
            newCareTeamNote.setText(careTeamSelected.getNote());
        else
            newCareTeamNote.setText(nodata);
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
            mStringArray[i] = lista.get(i).getId() + ". " + lista.get(i).getName();
        }

        return mStringArray;
    }

    public void representInitialSpinnerData(TextView newCareTeamName,
                                            TextView newCareTeamStatus, TextView newCareTeamTelecom,
                                            TextView newCareTeamNote) {

        String nodata="-";

        CareTeam careTeamSelected = getRestOfCareTeams().get(0);

        newCareTeamName.setText(careTeamSelected.getName());
        newCareTeamStatus.setText(careTeamSelected.getStatus());

        if(careTeamSelected.getTelecom() != 0)
            newCareTeamTelecom.setText(String.valueOf(careTeamSelected.getTelecom()));
        else
            newCareTeamTelecom.setText(nodata);

        if(careTeamSelected.getNote() != null)
            newCareTeamNote.setText(careTeamSelected.getNote());
        else
            newCareTeamNote.setText(nodata);
    }

    private CareTeam findCareTeam(Spinner spinnerCareTeam)
    {
        String idSelectedItem = spinnerCareTeam.getSelectedItem().toString().split(". ")[0];

        for(CareTeam ct : getRestOfCareTeams())
            if(idSelectedItem.equals(String.valueOf(ct.getId()))) return ct;
        return null;
    }
}