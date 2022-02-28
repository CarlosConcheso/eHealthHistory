package com.example.ehealthhistory.ui.Foootballer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ehealthhistory.BaseActivity;
import com.example.ehealthhistory.R;
import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerComunication;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import java.util.ArrayList;

public class MainFootballer extends BaseActivity {

    private ModelFactory mf = new ModelFactory();

    private FootballerComunication footballerComunication = mf.getFootballerComunication();
    private CareTeam careTeamROV = mf.getCareTeamROV();
    private Club club = mf.getClub();
    private FootballerContact contact = mf.getFootballerContact();
    private Footballer footballer = mf.getFootballer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_footballer);
        Bundle bundle = new Bundle();
        bundle.putString("Footballer", "Footballer's Menu");

        addHealthCareRows();

        // Informacion y campos de la pantalla
        final TextView nameActivityBase = (TextView) findViewById(R.id.nameActivityBase);
        final TextView footballerName = (TextView) findViewById(R.id.footballerName);
        final TextView footballerBirthDay = (TextView) findViewById(R.id.footballerBirthday);
        final TextView footballerTelcom = (TextView) findViewById(R.id.footballerTelcom);

        final Button buttonfootballerContact = (Button) findViewById(R.id.buttonfootballerContact);
        final Button buttonfootballerHealthCareDetails = (Button) findViewById(R.id.buttonfootballerHealthCareDetails);
        final Button buttonfootballerTeamCares = (Button) findViewById(R.id.buttonfootballerTeamCares);

        nameActivityBase.setText("eHealthHistory");

        footballerName.setText(footballer.getName());
        footballerBirthDay.setText(footballer.getBirthDate());
        footballerTelcom.setText(String.valueOf(footballer.getTelecom()));

        // Ver datos de contacto en profundidad
        buttonfootballerContact.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo(v.getContext(), UIFootballerContact.class);
            }

        }));

        // Ver historial médico en profundidad
        buttonfootballerHealthCareDetails.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo(v.getContext(), UIFootballerHealthCares.class);
            }

        }));

        // Ver médicos de confianza del futbolista: personales y del club.
        buttonfootballerTeamCares.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo(v.getContext(), UIFootballerFavsTeamCares.class);
            }

        }));
    }

    private void addHealthCareRows() {
        TableLayout tabla;
        int fila, colu = 1;
        tabla = (TableLayout) findViewById(R.id.TableHealthCareService);

        ArrayList<HealthCareService> healthCares = mf.getFootballer().getHealthcares();

        for (int i = 0; i < healthCares.size(); i++) {
            TableRow f = new TableRow(this);
            f.setId(i + 100);

            TextView col1 = new TextView(this);
            col1.setId(300 + i);
            if(healthCares.get(i).isActive())
                col1.setText("Si");
            else
                col1.setText("No");
            col1.setGravity(Gravity.CENTER);

            TextView col2 = new TextView(this);
            col2.setId(400 + i);
            col2.setText(mf.getHealthcares().get(i).getCategory());

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