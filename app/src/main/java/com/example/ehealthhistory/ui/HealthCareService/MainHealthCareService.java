package com.example.ehealthhistory.ui.HealthCareService;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.database.FireBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainHealthCareService extends BaseActivity {

    private final FireBase fb = new FireBase();
    private ArrayList<Footballer> footballers = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamcare_add_healthcare);
        String username = getIntent().getStringExtra("username");

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Añadir cuidado");

        // Identificar todos los componentes de la pantalla
        final Spinner spinnerFootballers = findViewById(R.id.spinnerHealthCareFootballers);

        final CheckBox activo = findViewById(R.id.checkBoxActivo);
        final CheckBox checkBoxAllDay = findViewById(R.id.checkBoxAllDay);

        final Spinner spinnerHealthCareCategory = findViewById(R.id.spinnerHealthCareCategory);
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
        final EditText multiLineHealthCareExtraDetails = findViewById(R.id.editTextTextMultiLineHealthCarExtraDetails);

        final Button botonAddHealthCare = findViewById(R.id.buttonAddHealthCare);

        // Inicializar Spinner con valores determinados para tratamientos
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoryHealthCare,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHealthCareCategory.setAdapter(adapter);

        // Inicializar futbolistas
        fb.representFootballersByCareTeam(username, spinnerFootballers, this);

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
                    if(checkDays(checkBoxL, checkBoxM, checkBoxX, checkBoxJ, checkBoxV, checkBoxS, checkBoxD)) {

                        fb.addHealthCareToFootballer(getFotballerById(spinnerFootballers.getSelectedItem().toString()),
                                activo, checkBoxAllDay, spinnerHealthCareCategory, healthCareName,
                                healthCareHoraInicio, healthCareHoraFin, healthCareMinsInicio, healthCareMinsFin,
                                checkBoxL, checkBoxM, checkBoxX, checkBoxJ, checkBoxV, checkBoxS, checkBoxD,
                                multiLineHealthCareCommentary, multiLineHealthCareExtraDetails);
                        finish();

                    }
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

    public void setFootballers(ArrayList<Footballer> footballers) {
        this.footballers = footballers;
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

    public String[] convert2Array(ArrayList<Footballer> lista)
    {
        String[] mStringArray = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            mStringArray[i] = lista.get(i).getId() + ". " + lista.get(i).getName();
        }

        return mStringArray;
    }

    public Footballer getFotballerById(String value)
    {
        int id = Integer.parseInt(value.split("\\.")[0]);

        for(Footballer f: footballers)
            if(id == f.getId()) {
                return f;
            }
        return null;
    }
}
