package com.example.ehealthhistory.UserInterface.Foootballer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ehealthhistory.UserInterface.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.Database.FireBase;

public class UIFootballerContact extends BaseActivity {

    FireBase fb =new FireBase();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_contact);
        
        String username = getIntent().getStringExtra("username");

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Datos de contacto");

        // Campos a rellenar con datos dinámicos FUTBOLISTA
        final TextView footballerContactName = findViewById(R.id.footballerContactName);
        final TextView footballerContactTelf = findViewById(R.id.footballerContactTelf);
        final TextView footballerContactLenguaje = findViewById(R.id.footballerContactLenguaje);
        final TextView footballerContactAdress = findViewById(R.id.footballerContactAdress);

        // Campos a rellenar con datos dinámicos CLUB
        final TextView footballerClubName = findViewById(R.id.footballerClubName);
        final TextView footballerClubAlias = findViewById(R.id.footballerClubAlias);
        final TextView footballerClubContactName = findViewById(R.id.footballerClubContactName);

        // Campos a rellenar con datos dinámicos EQUIPO MÉDICO CLUB
        final TextView footballerClubTeamCareName = findViewById(R.id.footballerClubTeamCareName);
        final TextView footballerClubTeamCareTelecom = findViewById(R.id.footballerClubTeamCareTelecom);
        final TextView footballerClubTeamCareNote = findViewById(R.id.footballerClubTeamCareNote);

        // Rellenando campos desde FireBase
        fb.representAllFootballerContact(username,
                footballerContactName, footballerContactTelf, footballerContactLenguaje, footballerContactAdress,
                footballerClubName, footballerClubAlias, footballerClubContactName,
                footballerClubTeamCareName, footballerClubTeamCareTelecom, footballerClubTeamCareNote);
    }
}
