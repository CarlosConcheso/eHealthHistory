package com.example.ehealthhistory.ui.Club;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.footballer.Footballer;

import java.util.ArrayList;

public class MainClub extends BaseActivity {

    private ModelFactory mf;
    private Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_club);

        Bundle bundle = new Bundle();
        bundle.putString("Care Team", "CareTeam's Menu");

        mf = new ModelFactory();
        club = mf.getClub();
        club.setFootballers(mf.getFootballers());

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mf.getClub().getName());

        // Elementos de la pantalla EQUIPO
        final TextView clubName = (TextView) findViewById(R.id.clubName);
        final TextView clubPresident = findViewById(R.id.clubPresident);
        final TextView clubAlias = findViewById(R.id.clubAlias);
        final TextView clubContact = findViewById(R.id.clubContact);
        final TextView clubTeamCare = findViewById(R.id.clubTeamCare);
        final Button buttonNewTeamCare = (Button) findViewById(R.id.buttonNewTeamCare);


        clubName.setText(club.getName());
        clubPresident.setText(club.getPresidente());
        clubAlias.setText(club.getAlias());
        clubContact.setText(club.getContactName());
        clubTeamCare.setText(club.getClubCareTeam().getName());

        addFootballersRows();

        // Acción del botón para ir a añadir nuevo equipo médico del club
        buttonNewTeamCare.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo(v.getContext(), UIAddNewCareTeam.class);
            }
        }));
    }

    private void addFootballersRows() {

        ArrayList<Footballer> footballers = mf.getClub().getFootballers();

        TableLayout tabla;
        int fila, colu = 1;
        tabla = (TableLayout) findViewById(R.id.TableClubFootballers);

        for (int i = 0; i < footballers.size(); i++) {
            TableRow f = new TableRow(this);
            f.setId(i + 100);

            TextView col1 = new TextView(this);
            col1.setId(300 + i);
            col1.setText(footballers.get(i).getName());

            TextView col2 = new TextView(this);
            col2.setId(400 + i);
            if(footballers.get(i).isActive())
                col2.setText("Si");
            else
                col2.setText("No");
            col2.setGravity(Gravity.CENTER);

            f.addView(col1);
            f.addView(col2);

            tabla.addView(f);
            colu = colu + 2;
        }
    }

    private static void changeTo(Context mContext, Class clase) {
        Intent intent = new Intent(mContext, clase);
        mContext.startActivity(intent);
    }
}