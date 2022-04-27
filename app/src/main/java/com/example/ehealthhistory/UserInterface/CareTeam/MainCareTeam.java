package com.example.ehealthhistory.UserInterface.CareTeam;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
import com.example.ehealthhistory.data.Model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.Model.footballer.Footballer;
import com.example.ehealthhistory.Database.FireBase;
import com.example.ehealthhistory.UserInterface.HealthCareService.MainHealthCareService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainCareTeam extends BaseActivity {

    private final FireBase fb = new FireBase();

    ArrayList<Footballer> footballers = new ArrayList<>();
    private CareTeam careTeam = new CareTeam();

    String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_careteam);
        String username = getIntent().getStringExtra("username");
        name = getIntent().getStringExtra("name");

        final TextView nameActivityBase =findViewById(R.id.nameActivityBase);
        nameActivityBase.setText(name);

        //Encontrar todoslo elementos de pantalla
        final TextView careTeamCIF = findViewById(R.id.careTeamCIF);
        final TextView careTeamStatus = findViewById(R.id.careTeamStatus);
        final TextView careTeamTelcom = findViewById(R.id.careTeamTelecom);
        final TextView careTeamNote = findViewById(R.id.careTeamNote);

        final Button addHealthCare2Footboller = findViewById(R.id.buttonAddHealthCareService);

        final EditText footballerName2Filter = findViewById(R.id.editTextNameFilter);
        final Button buttonReestartFilter = findViewById(R.id.buttonReestartFilter);

        // Establecer elementos en pantalla
        fb.representBasicDataAndCareTeamFootballer(username,
                careTeamCIF, careTeamStatus, careTeamTelcom, careTeamNote, this);

        buttonReestartFilter.setOnClickListener(v -> {
            addFootballersRows(getFootballers());
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

        // Ir a otra ventana donde añadir un cuidado personalizado al futbolista
        addHealthCare2Footboller.setOnClickListener((v ->
                changeTo(v.getContext(), username)));
    }

    @SuppressLint("SetTextI18n")
    public void addFootballersRows(ArrayList<Footballer> footballers) {
        TableLayout tabla;
        tabla = findViewById(R.id.TableCareTeamsFootballers);

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
            col2.setGravity(Gravity.CENTER);

            TextView col3 = new TextView(this);
            col3.setText(String.valueOf(footballers.get(i).getClub().getName()));
            col3.setGravity(Gravity.CENTER);
            col3.setPadding(25,0,0,0);


            f.addView(col1);
            f.addView(col2);
            f.addView(col3);

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

        for(Footballer footballer : getFootballers()) {
            if (footballer.getName().toUpperCase().contains(name.toUpperCase())) {
                footballeraux.add(footballer);
            }
        }
        return footballeraux;
    }

    public ArrayList<Footballer> getFootballers() {
        return footballers;
    }

    public void setFootballers(ArrayList<Footballer> footballers) {
        this.footballers = footballers;
    }

    private void changeTo(Context mContext, String username) {
        if(getFootballers().size()>0) {
            if(careTeam.getStatus().equals("activo"))
            {
                Intent intent = new Intent(mContext, MainHealthCareService.class);
                intent.putExtra("username", username);
                intent.putExtra("name", name);
                mContext.startActivity(intent);
            }
            else
                Snackbar.make(findViewById(R.id.buttonAddHealthCareService),
                        R.string.error_careteam_noactive, Snackbar.LENGTH_SHORT).show();
        }
        else
            Snackbar.make(findViewById(R.id.buttonAddHealthCareService),
                    R.string.error_usuario_futbolista, Snackbar.LENGTH_SHORT).show();
    }

    public CareTeam getCareTeam() {
        return careTeam;
    }

    private void unShowVirtualKeyboard(EditText editText, View view)
    {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}