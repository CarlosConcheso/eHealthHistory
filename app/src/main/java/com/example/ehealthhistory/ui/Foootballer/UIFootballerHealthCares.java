package com.example.ehealthhistory.ui.Foootballer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import com.example.ehealthhistory.database.FireBase;

import java.util.ArrayList;

public class UIFootballerHealthCares extends BaseActivity {

    ArrayList<HealthCareService> healthCares = new ArrayList<>();
    FireBase fb = new FireBase();

    private boolean consulta=false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_view_healthcareservices);

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Datos Médicos");
        String username = getIntent().getStringExtra("username");

        final Spinner spinnerHealthCare = findViewById(R.id.spinnerHealthCare);

        final TextView healthCareCategory = findViewById(R.id.healthCareCategory);
        final TextView healthCareName = findViewById(R.id.healthCareName);
        final TextView healthCareCommentary = findViewById(R.id.healthCareCommentary);
        final TextView healthCareAllDay = findViewById(R.id.healthCareAllDay);
        final TextView healthCareHoraInicio = findViewById(R.id.healthCareHoraInicio);
        final TextView healthCareHoraFin = findViewById(R.id.healthCareHoraFin);
        final TextView healthCareNote = findViewById(R.id.healthCareNote);

        // Rellena spinner con los HealthCares por FireBase
        fb.rellenarSpinnerHealthcareFootballer(username, spinnerHealthCare, this,
                healthCareCategory, healthCareName, healthCareCommentary,
                      healthCareAllDay, healthCareHoraInicio, healthCareHoraFin,healthCareNote);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerHealthCare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isConsulta()) {
                    establecerDatos(spinnerHealthCare, healthCareCategory, healthCareName, healthCareCommentary,
                            healthCareAllDay, healthCareHoraInicio, healthCareHoraFin, healthCareNote);
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            } });
    }

    @SuppressLint("SetTextI18n")
    public void establecerDatos(Spinner spinnerHealthCare, TextView healthCareCategory, TextView healthCareName,
                                TextView healthCareCommentary, TextView healthCareAllDay, TextView healthCareHoraInicio,
                                TextView healthCareHoraFin, TextView healthCareNote)
    {
        HealthCareService healthCareSelected = buscarHealthCare(spinnerHealthCare);

        assert healthCareSelected != null;
        healthCareCategory.setText(healthCareSelected.getCategory());
        healthCareName.setText(healthCareSelected.getName());
        healthCareCommentary.setText(healthCareSelected.getName());
        if(healthCareSelected.getAvalibleTime().isAllDay())
        {
            healthCareAllDay.setText("Si");
            healthCareHoraInicio.setText("");
            healthCareHoraFin.setText("");
        }
        else {
            healthCareAllDay.setText("No");
            healthCareHoraInicio.setText(healthCareSelected.getAvalibleTime().getAvalibleStartTime());
            healthCareHoraFin.setText(healthCareSelected.getAvalibleTime().getAvalibleEndTime());
        }
        healthCareNote.setText(healthCareSelected.getExtraDetails());
    }

    private HealthCareService buscarHealthCare(Spinner spinnerHealthCare)
    {
        String idSelectedItem = spinnerHealthCare.getSelectedItem().toString().split(". ")[0];

        for(HealthCareService hc : healthCares)
            if(idSelectedItem.equals(String.valueOf(hc.getId()))) return hc;
        return null;
    }

    public String[] convert2ArrayHealthCares(ArrayList<HealthCareService> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getId() + ". "+ lista.get(i).getName();
        }

        return mStringArray;
    }

    public ArrayList<HealthCareService> getHealthCares() {
        return healthCares;
    }

    public void setHealthCares(ArrayList<HealthCareService> healthCares) {
        this.healthCares = healthCares;
    }

    @SuppressLint("SetTextI18n")
    public void representarValorSpinnerInicial(TextView healthCareCategory, TextView healthCareName,
                                                TextView healthCareCommentary, TextView healthCareAllDay,
                                                TextView healthCareHoraInicio, TextView healthCareHoraFin,
                                                TextView healthCareNote)
    {
        HealthCareService hc1 = getHealthCares().get(0);

        healthCareCategory.setText(hc1.getCategory());
        healthCareName.setText(hc1.getName());
        healthCareCommentary.setText(hc1.getName());
        if(hc1.getAvalibleTime().isAllDay())
        {
            healthCareAllDay.setText("Si");
            healthCareHoraInicio.setText("");
            healthCareHoraFin.setText("");
        }
        else {
            healthCareAllDay.setText("No");
            healthCareHoraInicio.setText(hc1.getAvalibleTime().getAvalibleStartTime());
            healthCareHoraFin.setText(hc1.getAvalibleTime().getAvalibleEndTime());
        }
        healthCareNote.setText(hc1.getExtraDetails());
    }

    public boolean isConsulta() {
        return consulta;
    }

    public void setConsulta(boolean consulta) {
        this.consulta = consulta;
    }
}
