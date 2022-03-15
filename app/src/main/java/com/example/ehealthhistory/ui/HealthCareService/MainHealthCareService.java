package com.example.ehealthhistory.ui.HealthCareService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.ui.CareTeam.MainCareTeam;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainHealthCareService extends BaseActivity {

    private ModelFactory mf = new ModelFactory();
    private Footballer footballer = mf.getFootballer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamcare_add_healthcare);

        Bundle bundle = new Bundle();
        bundle.putString("Health Care Service", "Add Health Care Service");

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Añadir cuidado");

        // Identificar todos los componentes de la pantalla
        final Spinner healthCareFootballers = (Spinner) findViewById(R.id.spinnerHealthCareFootballers);

        final CheckBox activo = (CheckBox) findViewById(R.id.checkBoxActivo);
        final CheckBox checkBoxAllDay = (CheckBox) findViewById(R.id.checkBoxAllDay);

        final Spinner healthCareCategory = (Spinner) findViewById(R.id.spinnerHealthCareCategory);
        final EditText healthCareName = (EditText) findViewById(R.id.editTextHealthCareName);

        final Spinner healthCareHoraInicio = (Spinner) findViewById(R.id.spinnerTeamCareHoraInicio);
        final Spinner healthCareHoraFin = (Spinner) findViewById(R.id.spinnerTeamCareHoraFin);
        final Spinner healthCareMinsInicio = (Spinner) findViewById(R.id.spinnerTeamCareMinsInicio);
        final Spinner healthCareMinsFin = (Spinner) findViewById(R.id.spinnerTeamCareMinsFin);

        final CheckBox checkBoxL = (CheckBox) findViewById(R.id.checkBoxLunes);
        final CheckBox checkBoxM = (CheckBox) findViewById(R.id.checkBoxMartes);
        final CheckBox checkBoxX = (CheckBox) findViewById(R.id.checkBoxMiercoles);
        final CheckBox checkBoxJ = (CheckBox) findViewById(R.id.checkBoxJueves);
        final CheckBox checkBoxV = (CheckBox) findViewById(R.id.checkBoxViernes);
        final CheckBox checkBoxS = (CheckBox) findViewById(R.id.checkBoxSabado);
        final CheckBox checkBoxD = (CheckBox) findViewById(R.id.checkBoxDomingo);

        final EditText multiLineHealthCareCommentary = (EditText) findViewById(R.id.editTextTextMultiLineHealthCareCommentary);
        final Button botonAddHealthCare = (Button) findViewById(R.id.buttonAddHealthCare);

        ArrayList<String> categorias = new ArrayList<String>();
        categorias.add("Recuperacion");
        categorias.add("Manutención");
        categorias.add("Ingreso");

        // Inicializar Spinners de la app
        inicializarFootballers(healthCareFootballers, mf.getFootballers());
        inicializarCategorias(healthCareCategory, categorias);

        //Se selecciona el cuidado 24 horas
        checkBoxAllDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ((CheckBox)v).isChecked() ) {
                    anularSpinnersHora(healthCareHoraInicio,healthCareHoraFin,healthCareMinsInicio, healthCareMinsFin);
                }
                else
                    activarSpinnersHora(healthCareHoraInicio,healthCareHoraFin,healthCareMinsInicio, healthCareMinsFin);
            }
        });

        // Boton añadir
        botonAddHealthCare.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCompleted(healthCareName,multiLineHealthCareCommentary))
                    if(!checkHours(checkBoxAllDay, healthCareHoraInicio, healthCareHoraFin, healthCareMinsInicio, healthCareMinsFin))
                        Snackbar.make(findViewById(R.id.buttonAddHealthCare),
                                R.string.error_usuario_horas, Snackbar.LENGTH_SHORT).show();
                    else
                        if(checkDays(checkBoxL, checkBoxM, checkBoxX, checkBoxJ, checkBoxV, checkBoxS, checkBoxD))
                            finish();
                        else
                            Snackbar.make(findViewById(R.id.buttonAddHealthCare),
                                    R.string.error_usuario_sindias, Snackbar.LENGTH_SHORT).show();
                else
                    Snackbar.make(findViewById(R.id.buttonAddHealthCare),
                            R.string.error_usuario_camposvacios, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }

    private boolean checkCompleted(EditText name, EditText commentary)
    {
        if(name.getText().length()>0 && commentary.getText().length()>0)
            return true;
        else
            return false;
    }

    private boolean checkHours(CheckBox checkBoxAllDay, Spinner healthCareHoraInicio, Spinner healthCareHoraFin,
                               Spinner healthCareMinsInicio, Spinner healthCareMinsFin)
    {
        if(checkBoxAllDay.isChecked())
            return true;
        else
        {
            if(checkSpinnerHours(healthCareHoraInicio, healthCareHoraFin,healthCareMinsInicio, healthCareMinsFin))
                    return true;
            return false;
        }
    }

    private boolean checkDays(CheckBox... checks)
    {
        boolean selected = false;
        for(CheckBox c: checks)
            if(c.isChecked())
            {
                selected=true;
            }
        return selected;
    }

    private boolean checkSpinnerHours(Spinner healthCareHoraInicio, Spinner healthCareHoraFin,
                                      Spinner healthCareMinsInicio, Spinner healthCareMinsFin)
    {

        int horaI = Integer.parseInt(healthCareHoraInicio.getSelectedItem().toString());
        int horaF = Integer.parseInt(healthCareHoraFin.getSelectedItem().toString());
        int minsI = Integer.parseInt(healthCareMinsInicio.getSelectedItem().toString());
        int minsF = Integer.parseInt(healthCareMinsFin.getSelectedItem().toString());

        if(horaI > horaF)
            return false;
        else if(horaI < horaF)
            return true;
        else
            if(minsI > minsF)
                return false;
            else if(minsI == minsF)
                return false;
            else
                return true;
    }

    //----------------------------------------------------------------------------------------------
    // Metodos para modififcar los spinners de las horass
    private void anularSpinnersHora(Spinner horaInicio, Spinner horaFin, Spinner minsInicio, Spinner minsFin)
    {
        horaInicio.setEnabled(false);
        horaFin.setEnabled(false);
        minsInicio.setEnabled(false);
        minsFin.setEnabled(false);
    }

    private void activarSpinnersHora(Spinner horaInicio, Spinner horaFin, Spinner minsInicio, Spinner minsFin)
    {
        horaInicio.setEnabled(true);
        horaFin.setEnabled(true);
        minsInicio.setEnabled(true);
        minsFin.setEnabled(true);
    }

    // Metodos para inicializar spinners y convertir en arrays
    private void inicializarFootballers(Spinner spinner, ArrayList<Footballer> lista)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                convert2ArrayFootballer(lista));

        spinner.setAdapter(adapter);
    }

    private String[] convert2ArrayFootballer(ArrayList<Footballer> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getName();
        }

        return mStringArray;
    }

    private void inicializarCategorias(Spinner spinner, ArrayList<String> lista)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                convert2ArrayCategoria(lista));

        spinner.setAdapter(adapter);
    }

    private String[] convert2ArrayCategoria(ArrayList<String> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i);
        }

        return mStringArray;
    }
}
