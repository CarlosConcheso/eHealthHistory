package com.example.ehealthhistory.ui.Foootballer;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;

import java.util.ArrayList;

public class UIFootballerHealthCares extends BaseActivity {

    ModelFactory mf = new ModelFactory();
    ArrayList<HealthCareService> healthCares = mf.getHealthcares();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_view_healthcareservices);
        Bundle bundle = new Bundle();
        bundle.putString("Footballer's Health Cares", "Footballer's Health Cares");

        final TextView nameActivityBase = (TextView) findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Datos Médicos");

        final Spinner spinnerHealthCare = (Spinner)findViewById(R.id.spinnerHealthCare);

        final TextView healthCareCategory = (TextView) findViewById(R.id.healthCareCategory);
        final TextView healthCareName = (TextView) findViewById(R.id.healthCareName);
        final TextView healthCareCommentary = (TextView) findViewById(R.id.healthCareCommentary);
        final TextView healthCareAllDay = (TextView) findViewById(R.id.healthCareAllDay);
        final TextView healthCareHoraInicio = (TextView) findViewById(R.id.healthCareHoraInicio);
        final TextView healthCareHoraFin = (TextView) findViewById(R.id.healthCareHoraFin);
        final TextView healthCareNote = (TextView) findViewById(R.id.healthCareNote);

        inicializarHealthCares(spinnerHealthCare, healthCares);

        // El spinner se actualiza cada vez que cambiamos el valor
        spinnerHealthCare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                establecerDatos(spinnerHealthCare, healthCareCategory, healthCareName, healthCareCommentary,
                        healthCareAllDay, healthCareHoraInicio, healthCareHoraFin,healthCareNote);
            }
            public void onNothingSelected(AdapterView<?> adapterView) { return; } });
    }

    private  void establecerDatos(Spinner spinnerHealthCare, TextView healthCareCategory, TextView healthCareName,
                                  TextView healthCareCommentary,TextView healthCareAllDay, TextView healthCareHoraInicio,
                                  TextView healthCareHoraFin, TextView healthCareNote)
    {
        HealthCareService healthCareSelected = buscarHealthCare(spinnerHealthCare);

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
        String nombreHealthCare = spinnerHealthCare.getSelectedItem().toString();

        for(HealthCareService hc : healthCares)
            if(nombreHealthCare == hc.getName()) {
                return hc;
            }
        return null;
    }

    private void inicializarHealthCares(Spinner spinner, ArrayList<HealthCareService> lista)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                convert2ArrayHealthCares(lista));

        spinner.setAdapter(adapter);
    }

    private String[] convert2ArrayHealthCares(ArrayList<HealthCareService> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getName();
        }

        return mStringArray;
    }
}
