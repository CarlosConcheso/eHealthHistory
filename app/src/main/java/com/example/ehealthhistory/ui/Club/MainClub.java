package com.example.ehealthhistory.ui.Club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.database.FireBase;

import java.util.ArrayList;

public class MainClub extends BaseActivity {

    private final FireBase fb = new FireBase();
    private Club club = new Club();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_club);
        String username = getIntent().getStringExtra("username");

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);

        // Elementos de la pantalla EQUIPO
        final TextView clubName = findViewById(R.id.clubName);
        final TextView clubPresident = findViewById(R.id.clubPresident);
        final TextView clubAlias = findViewById(R.id.clubAlias);
        final TextView clubContact = findViewById(R.id.clubContact);
        final TextView clubActive = findViewById(R.id.clubActive);
        final TextView clubTeamCare = findViewById(R.id.clubTeamCare);
        final Button buttonNewTeamCare = findViewById(R.id.buttonNewTeamCare);

        fb.representBasicDataAndClubsFootballer(username, nameActivityBase,
                clubName, clubPresident, clubAlias, clubContact, clubActive, clubTeamCare, this);

        // Acción del botón para ir a añadir nuevo equipo médico del club
        buttonNewTeamCare.setOnClickListener((v -> changeTo(v.getContext(), username)));
    }

    @SuppressLint("SetTextI18n")
    public void addFootballersRows(ArrayList<Footballer> footballers) {

        TableLayout tabla;
        tabla = findViewById(R.id.TableClubFootballers);

        for (int i = 0; i < footballers.size(); i++) {
            TableRow f = new TableRow(this);

            TextView col1 = new TextView(this);
            col1.setText(footballers.get(i).getName());
            col1.setPadding(25,1,1,1);

            TextView col2 = new TextView(this);
            if(footballers.get(i).isActive())
                col2.setText("Si");
            else
                col2.setText("No");
            col2.setGravity(Gravity.CENTER);

            f.addView(col1);
            f.addView(col2);

            tabla.addView(f);
        }
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    private static void changeTo(Context mContext, String username) {
        Intent intent = new Intent(mContext, UIAddNewCareTeam.class);
        intent.putExtra("username", username);
        mContext.startActivity(intent);
    }
}