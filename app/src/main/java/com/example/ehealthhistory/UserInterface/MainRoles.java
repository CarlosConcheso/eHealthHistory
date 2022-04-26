package com.example.ehealthhistory.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ehealthhistory.R;
import com.example.ehealthhistory.Database.FireBase;
import com.example.ehealthhistory.UserInterface.CareTeam.MainCareTeam;
import com.example.ehealthhistory.UserInterface.Club.MainClub;
import com.example.ehealthhistory.UserInterface.Foootballer.MainFootballer;

public class MainRoles extends BaseActivity {

    private FireBase fb = new FireBase();
    private String username;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_roles);
        username = getIntent().getStringExtra("username");
        fb = new FireBase();

        final Button botonFutbolista = findViewById(R.id.buttonFutbolista);
        final Button botonMedico = findViewById(R.id.buttonMedico);
        final Button botonClub = findViewById(R.id.buttonClub);

        final TextView textoBanner = findViewById(R.id.nameActivityBase);
        fb.getNameFromUser(username, textoBanner, this);
        habilitarBotonesRoles(botonFutbolista, botonMedico, botonClub);

        botonFutbolista.setOnClickListener((v -> changeTo(v.getContext(), MainFootballer.class, username, name)));

        botonMedico.setOnClickListener((v -> changeTo(v.getContext(), MainCareTeam.class, username, name)));

        botonClub.setOnClickListener((v -> changeTo(v.getContext(), MainClub.class, username, name)));
    }

    private void habilitarBotonesRoles(Button botonFutbolista, Button botonMedico, Button botonClub)
    {
        fb.getRolesFromUsername(username,botonFutbolista,botonMedico,botonClub);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("rawtypes")
    private static void changeTo(Context mContext, Class clase, String username, String nameOfUser) {
        Intent intent = new Intent(mContext, clase);
        intent.putExtra("username", username);
        intent.putExtra("name", nameOfUser);
        mContext.startActivity(intent);
    }
}