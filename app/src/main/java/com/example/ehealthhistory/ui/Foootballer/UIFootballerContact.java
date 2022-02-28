package com.example.ehealthhistory.ui.Foootballer;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;

public class UIFootballerContact extends BaseActivity {


    ModelFactory mf = new ModelFactory();
    private Footballer footballer = mf.getFootballer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_contact);


        Bundle bundle = new Bundle();
        bundle.putString("Footballer's Contact", "Footballer's Contact");

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
        footballerContactName.setText(footballer.getFootballerContact().getName());
        footballerContactTelf.setText(String.valueOf(footballer.getFootballerContact().getTelecom()));
        footballerContactLenguaje.setText(footballer.getFootballerComunication().getLenguage());
        footballerContactAdress.setText(footballer.getFootballerContact().getAdress());

        footballerClubName.setText(footballer.getFootballerContact().getClub().getName());
        footballerClubAlias.setText(footballer.getFootballerContact().getClub().getAlias());
        footballerClubContactName.setText(footballer.getFootballerContact().getClub().getContactName());

        footballerClubTeamCareName.setText(footballer.getFootballerContact().getClub().getClubCareTeam().getName());
        footballerClubTeamCareTelecom.setText(String.valueOf(footballer.getFootballerContact().getClub().getClubCareTeam().getTelcom()));
        footballerClubTeamCareNote.setText(footballer.getFootballerContact().getClub().getClubCareTeam().getNote());



    }
}
