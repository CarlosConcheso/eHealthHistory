package com.example.ehealthhistory.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.database.FireBase;
import com.example.ehealthhistory.ui.CareTeam.MainCareTeam;
import com.example.ehealthhistory.ui.Club.MainClub;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.example.ehealthhistory.ui.HealthCareService.MainHealthCareService;

import java.util.ArrayList;

public class MainRoles extends BaseActivity {

    private FireBase fb = new FireBase();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_roles);
        getSupportActionBar().hide();

        username = getIntent().getStringExtra("username");

        final Button botonFutbolista = (Button) findViewById(R.id.buttonFutbolista);
        final Button botonMedico = (Button) findViewById(R.id.buttonMedico);
        final Button botonClub = (Button) findViewById(R.id.buttonClub);

        final TextView textoBanner = (TextView) findViewById(R.id.nameActivityBase);
        textoBanner.setText("Bienvenido " + fb.getName(username));

        botonClub.setVisibility(View.VISIBLE);
        botonFutbolista.setVisibility(View.VISIBLE);
        botonMedico.setVisibility(View.VISIBLE);
        //habilitarBotonesRoles(botonFutbolista, botonMedico, botonClub);

        botonFutbolista.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeTo(v.getContext(), MainFootballer.class, username); }
        }));

        botonMedico.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeTo(v.getContext(), MainCareTeam.class, username); }
        }));

        botonClub.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo(v.getContext(), MainClub.class, username);
            }
        }));
    }

    private void habilitarBotonesRoles(Button botonFutbolista, Button botonMedico, Button botonClub)
    {
        ArrayList<String> roles = fb.getRolesOfUsername(username);

        for(String rol : roles) {
            if (rol == "club")
                botonClub.setVisibility(View.VISIBLE);

            else if (rol == "footballer")
                botonFutbolista.setVisibility(View.VISIBLE);

            else if (rol == "careteam")
                botonMedico.setVisibility(View.VISIBLE);
        }
    }

    private static void changeTo(Context mContext, Class clase, String username) {
        Intent intent = new Intent(mContext, clase);
        intent.putExtra("username", username);
        mContext.startActivity(intent);
    }
}