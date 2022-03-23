package com.example.ehealthhistory.ui.Foootballer;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.example.ehealthhistory.database.FireBase;

public class UIFootballerContact extends BaseActivity {


    ModelFactory mf = new ModelFactory();
    private Footballer footballer = mf.getFootballer();

    FireBase fb;
    private Footballer futbolista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_contact);
        
        fb = new FireBase();
        futbolista = new Footballer();
        futbolista.setUsername(getIntent().getStringExtra("username"));

        final TextView nameActivityBase = (TextView) findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Datos de contacto");

        // Campos a rellenar con datos dinámicos FUTBOLISTA
        final TextView footballerContactName = (TextView) findViewById(R.id.footballerContactName);
        final TextView footballerContactTelf = (TextView) findViewById(R.id.footballerContactTelf);
        final TextView footballerContactLenguaje = (TextView) findViewById(R.id.footballerContactLenguaje);
        final TextView footballerContactAdress = (TextView) findViewById(R.id.footballerContactAdress);

        // Campos a rellenar con datos dinámicos CLUB
        final TextView footballerClubName = (TextView) findViewById(R.id.footballerClubName);
        final TextView footballerClubAlias = (TextView) findViewById(R.id.footballerClubAlias);
        final TextView footballerClubContactName = (TextView) findViewById(R.id.footballerClubContactName);

        // Campos a rellenar con datos dinámicos EQUIPO MÉDICO CLUB
        final TextView footballerClubTeamCareName = (TextView) findViewById(R.id.footballerClubTeamCareName);
        final TextView footballerClubTeamCareTelecom = (TextView) findViewById(R.id.footballerClubTeamCareTelecom);
        final TextView footballerClubTeamCareNote = (TextView) findViewById(R.id.footballerClubTeamCareNote);

        // Rellenando campos
        futbolista = fb.getFootballer(futbolista.getUsername());
        footballerContactName.setText(futbolista.getFootballerContact().getName());
        footballerContactTelf.setText(String.valueOf(futbolista.getFootballerContact().getTelecom()));
        footballerContactLenguaje.setText(futbolista.getFootballerComunication().getLenguage());
        footballerContactAdress.setText(futbolista.getFootballerContact().getAdress());

        futbolista.setClub(fb.getFootballerClub(futbolista.getUsername()));
        footballerClubName.setText(futbolista.getClub().getName());
        footballerClubAlias.setText(futbolista.getClub().getAlias());
        footballerClubContactName.setText(futbolista.getClub().getContactName());

        futbolista.getClub().setClubCareTeam(fb.getClubCareTeam(futbolista.getClub().getName()));
        footballerClubTeamCareName.setText(futbolista.getClub().getClubCareTeam().getName());
        footballerClubTeamCareTelecom.setText(String.valueOf(futbolista.getClub().getClubCareTeam().getTelcom()));
        footballerClubTeamCareNote.setText(futbolista.getClub().getClubCareTeam().getNote());
    }
}
