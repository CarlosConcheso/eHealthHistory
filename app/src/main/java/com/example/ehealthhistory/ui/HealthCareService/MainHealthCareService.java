package com.example.ehealthhistory.ui.HealthCareService;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainHealthCareService extends BaseActivity {

    private ModelFactory mf = new ModelFactory();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamcare_add_healthcare);

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Añadir cuidado");

        // Identificar todos los componentes de la pantalla
        final Spinner healthCareFootballers = findViewById(R.id.spinnerHealthCareFootballers);

        final CheckBox activo = findViewById(R.id.checkBoxActivo);
        final CheckBox checkBoxAllDay = findViewById(R.id.checkBoxAllDay);

        final Spinner healthCareCategory = findViewById(R.id.spinnerHealthCareCategory);
        final EditText healthCareName = findViewById(R.id.editTextHealthCareName);

        final Spinner healthCareHoraInicio = findViewById(R.id.spinnerTeamCareHoraInicio);
        final Spinner healthCareHoraFin = findViewById(R.id.spinnerTeamCareHoraFin);
        final Spinner healthCareMinsInicio = findViewById(R.id.spinnerTeamCareMinsInicio);
        final Spinner healthCareMinsFin = findViewById(R.id.spinnerTeamCareMinsFin);

        final CheckBox checkBoxL = findViewById(R.id.checkBoxLunes);
        final CheckBox checkBoxM = findViewById(R.id.checkBoxMartes);
        final CheckBox checkBoxX = findViewById(R.id.checkBoxMiercoles);
        final CheckBox checkBoxJ = findViewById(R.id.checkBoxJueves);
        final CheckBox checkBoxV = findViewById(R.id.checkBoxViernes);
        final CheckBox checkBoxS = findViewById(R.id.checkBoxSabado);
        final CheckBox checkBoxD = findViewById(R.id.checkBoxDomingo);

        final EditText multiLineHealthCareCommentary = findViewById(R.id.editTextTextMultiLineHealthCareCommentary);
        final Button botonAddHealthCare = findViewById(R.id.buttonAddHealthCare);

        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Recuperacion");
        categorias.add("Manutención");
        categorias.add("Ingreso");

        // Inicializar Spinners de la app
        inicializarFootballers(healthCareFootballers, mf.getFootballers());
        inicializarCategorias(healthCareCategory, categorias);

        //Se selecciona el cuidado 24 horas
        checkBoxAllDay.setOnClickListener(v -> {
            if ( ((CheckBox)v).isChecked() ) {
                anularSpinnersHora(healthCareHoraInicio,healthCareHoraFin,healthCareMinsInicio, healthCareMinsFin);
            }
            else
                activarSpinnersHora(healthCareHoraInicio,healthCareHoraFin,healthCareMinsInicio, healthCareMinsFin);
        });

        // Boton añadir
        botonAddHealthCare.setOnClickListener((v -> {
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
        }));
    }

    private boolean checkCompleted(EditText name, EditText commentary)
    {
        return name.getText().length() > 0 && commentary.getText().length() > 0;
    }

    private boolean checkHours(CheckBox checkBoxAllDay, Spinner healthCareHoraInicio, Spinner healthCareHoraFin,
                               Spinner healthCareMinsInicio, Spinner healthCareMinsFin)
    {
        if(checkBoxAllDay.isChecked())
            return true;
        else
        {
            return checkSpinnerHours(healthCareHoraInicio, healthCareHoraFin, healthCareMinsInicio, healthCareMinsFin);
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
            else return minsI != minsF;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
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
