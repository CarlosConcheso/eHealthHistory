package com.example.ehealthhistory.ui.CareTeam;
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

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.database.FireBase;
import com.example.ehealthhistory.ui.HealthCareService.MainHealthCareService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainCareTeam extends BaseActivity {

    private final FireBase fb = new FireBase();
    ArrayList<Footballer> footballers = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_careteam);
        String username = getIntent().getStringExtra("username");

        final TextView nameActivityBase =findViewById(R.id.nameActivityBase);
        nameActivityBase.setText("Equipo Médico");

        //Encontrar todoslo elementos de pantalla
        final TextView careTeamName = findViewById(R.id.careTeamName);
        final TextView careTeamStatus = findViewById(R.id.careTeamStatus);
        final TextView careTeamTelcom = findViewById(R.id.careTeamTelecom);
        final TextView careTeamNote = findViewById(R.id.careTeamNote);

        final Button addHealthCare2Footboller = findViewById(R.id.buttonAddHealthCareService);

        final EditText footballerName2Filter = findViewById(R.id.editTextNameFilter);
        final Button buttonReestartFilter = findViewById(R.id.buttonReestartFilter);


        // Establecer elementos en pantalla
        fb.representBasicDataAndCareTeamFootballer(username, nameActivityBase,
                careTeamName, careTeamStatus, careTeamTelcom, careTeamNote, this);

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
            col1.setPadding(25,1,1,1);

            TextView col2 = new TextView(this);
            if(footballers.get(i).isActive())
                col2.setText("Si");
            else
                col2.setText("No");
            col2.setGravity(Gravity.CENTER);
            col2.setPadding(25,1,1,1);


            TextView col3 = new TextView(this);
            col3.setText(String.valueOf(footballers.get(i).getFootballerContact().getTelecom()));
            col3.setGravity(Gravity.CENTER);
            col3.setPadding(25,1,1,1);

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

        for(Footballer footballer : getFootballers())
            if(footballer.getName().toUpperCase().contains(name.toUpperCase()))
                footballeraux.add(footballer);

            return footballeraux;
    }

    public ArrayList<Footballer> getFootballers() {
        return footballers;
    }

    public void setFootballers(ArrayList<Footballer> footballers) {
        this.footballers = footballers;
    }

    public void addFootballer(Footballer footballer)
    {
        getFootballers().add(footballer);
    }

    private void changeTo(Context mContext, String username) {
        if(getFootballers().size()>0) {
            Intent intent = new Intent(mContext, MainHealthCareService.class);
            intent.putExtra("username", username);
            mContext.startActivity(intent);
        }
        else
            Snackbar.make(findViewById(R.id.buttonAddHealthCareService),
                    R.string.error_usuario_futbolista, Snackbar.LENGTH_SHORT).show();
    }

    private void unShowVirtualKeyboard(EditText editText, View view)
    {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}