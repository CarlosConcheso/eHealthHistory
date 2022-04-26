package com.example.ehealthhistory.UserInterface.Club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ehealthhistory.UserInterface.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.Model.Club.Club;
import com.example.ehealthhistory.data.Model.footballer.Footballer;
import com.example.ehealthhistory.Database.FireBase;

import java.util.ArrayList;

public class MainClub extends BaseActivity {

    private final FireBase fb = new FireBase();
    private Club club = new Club();
    String username;


    @Override
    protected void onResume() {
        super.onResume();
        final TextView clubTeamCare = findViewById(R.id.clubTeamCare);

        fb.refreshClubCareTeam(username, clubTeamCare);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_club);
        username = getIntent().getStringExtra("username");

        final TextView nameActivityBase = findViewById(R.id.nameActivityBase);

        // Elementos de la pantalla EQUIPO
        final TextView clubCIF = findViewById(R.id.clubCIF);
        final TextView clubPresident = findViewById(R.id.clubPresident);
        final TextView clubAlias = findViewById(R.id.clubAlias);
        final TextView clubContact = findViewById(R.id.clubContact);
        final TextView clubActive = findViewById(R.id.clubActive);
        final TextView clubTeamCare = findViewById(R.id.clubTeamCare);
        final Button buttonNewTeamCare = findViewById(R.id.buttonNewTeamCare);

        final EditText footballerName2Filter = findViewById(R.id.editTextNameFilter);
        final Button buttonReestartFilter = findViewById(R.id.buttonReestartFilter);

        fb.representBasicDataAndClubsFootballer(username, nameActivityBase,
                clubCIF, clubPresident, clubAlias, clubContact, clubActive, clubTeamCare, this);

        buttonReestartFilter.setOnClickListener(v -> {
            addFootballersRows(club.getFootballers());
            footballerName2Filter.setText("");
            unShowVirtualKeyboard(footballerName2Filter,v);
        });

        footballerName2Filter.setOnKeyListener((v, keyCode, event) -> {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    filterTable(footballerName2Filter);
                    unShowVirtualKeyboard(footballerName2Filter, v);
                    return true;
                }
                return false;
            });

        // Acción del botón para ir a añadir nuevo equipo médico del club
        buttonNewTeamCare.setOnClickListener((v -> changeTo(v.getContext(), username)));
    }

    @SuppressLint("SetTextI18n")
    public void addFootballersRows(ArrayList<Footballer> footballers) {

        TableLayout tabla;
        tabla = findViewById(R.id.TableClubFootballers);

        tabla.removeAllViews();
        
            for (int i = 0; i < footballers.size(); i++) {
            TableRow f = new TableRow(this);

            TextView col1 = new TextView(this);
            col1.setText(footballers.get(i).getName());
            col1.setPadding(25,0,0,0);

            TextView col2 = new TextView(this);
            if(footballers.get(i).isActive())
                col2.setText("Si");
            else
                col2.setText("No");
            col2.setPadding(210,0,0,0);

                f.addView(col1);
            f.addView(col2);

            tabla.addView(f);
        }
    }

    private void filterTable(EditText footballerName2Filter) {
        ArrayList<Footballer> footballersaux = getFootballersByName(footballerName2Filter.getText().toString());

        addFootballersRows(footballersaux);
    }

    private ArrayList<Footballer> getFootballersByName(String name)
    {
        ArrayList<Footballer> footballeraux = new ArrayList<>();

        for(Footballer footballer : club.getFootballers()) {
            if (footballer.getName().toUpperCase().contains(name.toUpperCase()))
            {
                footballeraux.add(footballer);
            }
        }

            return footballeraux;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void addFootballer(Footballer fut)
    {
        club.getFootballers().add(fut);
    }

    private static void changeTo(Context mContext, String username) {
        Intent intent = new Intent(mContext, UIAddNewCareTeam.class);
        intent.putExtra("username", username);
        mContext.startActivity(intent);
    }

    private void unShowVirtualKeyboard(EditText editText, View view)
    {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}