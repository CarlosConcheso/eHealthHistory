package com.example.ehealthhistory.UserInterface.Foootballer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ehealthhistory.UserInterface.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.Model.footballer.Footballer;
import com.example.ehealthhistory.data.Model.healthCareService.HealthCareService;
import com.example.ehealthhistory.Database.FireBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainFootballer extends BaseActivity {

    FireBase fb = new FireBase();
    private Footballer footballer = new Footballer();
    private String username;
    private String name;

    private ArrayList<HealthCareService> healthCaresServices = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_footballer);
        username = getIntent().getStringExtra("username");
        name = getIntent().getStringExtra("name");

        final TextView nameActivityBase =findViewById(R.id.nameActivityBase);
        nameActivityBase.setText(name);

        // Informacion y campos de la pantalla
        final TextView footballerDNI = findViewById(R.id.footballerDNI);
        final TextView footballerBirthDay = findViewById(R.id.footballerBirthday);
        final TextView footballerTelcom = findViewById(R.id.footballerTelcom);

        final Button buttonfootballerContact = findViewById(R.id.buttonfootballerContact);
        final Button buttonfootballerHealthCareDetails = findViewById(R.id.buttonfootballerHealthCareDetails);
        final Button buttonfootballerTeamCares = findViewById(R.id.buttonfootballerTeamCares);

        // Datos representados con Firebase
        fb.representBasicFootballerHealthCares(username, this);
        fb.representFootballerBasicData(username, footballer, footballerDNI, footballerBirthDay,footballerTelcom, this);

        // Ver datos de contacto en profundidad
        buttonfootballerContact.setOnClickListener((v -> changeTo(v.getContext(), UIFootballerContact.class, username)));

        // Ver historial médico en profundidad
        buttonfootballerHealthCareDetails.setOnClickListener((v -> changeToHealthCares(v.getContext(), username)));

        // Ver médicos de confianza del futbolista: personales y del club.
        buttonfootballerTeamCares.setOnClickListener((v -> changeTo(v.getContext(), UIFootballerFavsCareTeams.class, username)));
    }

    @SuppressLint("SetTextI18n")
    public void addHealthCareRows(ArrayList<HealthCareService> healthCares) {
        TableLayout tabla;
        tabla = findViewById(R.id.TableHealthCareService);

        for (int i = 0; i < healthCares.size(); i++) {
            TableRow f = new TableRow(this);

            TextView col1 = new TextView(this);
            if(healthCares.get(i).isActive())
                col1.setText("Si");
            else
                col1.setText("No");
            col1.setGravity(Gravity.CENTER);

            TextView col2 = new TextView(this);
            col2.setText(healthCares.get(i).getCategory());
            col2.setGravity(Gravity.CENTER);

            f.addView(col1);
            f.addView(col2);

            tabla.addView(f);
        }
    }

    public ArrayList<HealthCareService> getHealthCaresServices() {
        return healthCaresServices;
    }

    public void setHealthCaresServices(ArrayList<HealthCareService> healthCaresServices) {
        this.healthCaresServices = healthCaresServices;
    }

    @SuppressWarnings("rawtypes")
    private void changeTo(Context mContext, Class clase, String footballer) {
        Intent intent = new Intent(mContext, clase);
        intent.putExtra("username", footballer);
        mContext.startActivity(intent);
    }

    private void changeToHealthCares(Context mContext, String footballer) {
        if(getHealthCaresServices().size()>0) {
            Intent intent = new Intent(mContext, UIFootballerHealthCares.class);
            intent.putExtra("username", footballer);
            mContext.startActivity(intent);
        }
        else
            Snackbar.make(findViewById(R.id.buttonfootballerHealthCareDetails),
                    R.string.error_usuario_healthcares, Snackbar.LENGTH_SHORT).show();
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }
}