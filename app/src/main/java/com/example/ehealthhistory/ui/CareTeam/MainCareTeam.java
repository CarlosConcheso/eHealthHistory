package com.example.ehealthhistory.ui.CareTeam;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
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
import com.example.ehealthhistory.data.model.ModelFactory;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;
import com.example.ehealthhistory.ui.Foootballer.UIFootballerFavsTeamCares;
import com.example.ehealthhistory.ui.HealthCareService.MainHealthCareService;

public class MainCareTeam extends BaseActivity {

    private ModelFactory mf = new ModelFactory();
    private CareTeam ct = mf.getCareTeamROV();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_careteam);

        Bundle bundle = new Bundle();
        bundle.putString("Care Team", "CareTeam's Menu");

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final TextView nameActivityBase =findViewById(R.id.nameActivityBase);

        //Encontrar todoslo elementos de pantalla
        final TextView careTeamName = (TextView) findViewById(R.id.careTeamName);
        final TextView careTeamStatus = (TextView) findViewById(R.id.careTeamStatus);
        final TextView careTeamTelcom = (TextView) findViewById(R.id.careTeamTelecom);
        final TextView careTeamNote = (TextView) findViewById(R.id.careTeamNote);

        final Button addHealthCare2Footboller = (Button) findViewById(R.id.buttonAddHealthCareService);

        // Establecer elementos en pantalla
        careTeamName.setText(ct.getName());
        careTeamStatus.setText(ct.getStatus().toString());
        careTeamTelcom.setText(String.valueOf(ct.getTelcom()));
        careTeamNote.setText(ct.getNote());

        addFootballersRows();

        // Ir a otra ventana donde añadir un cuidado personalizado al futbolista
        addHealthCare2Footboller.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo(v.getContext(), MainHealthCareService.class);
            }

        }));
    }

    private void addFootballersRows() {
        TableLayout tabla;
        int fila, colu = 1;
        tabla = (TableLayout) findViewById(R.id.TableCareTeamsFootballers);

        for (int i = 0; i < mf.getFootballers().size(); i++) {
            TableRow f = new TableRow(this);
            f.setId(i + 100);

            TextView col1 = new TextView(this);
            col1.setId(300 + i);
            col1.setText(mf.getFootballers().get(i).getName());

            TextView col2 = new TextView(this);
            col2.setId(400 + i);
            if(mf.getFootballers().get(i).isActive())
                col2.setText("Si");
            else
                col2.setText("No");
            col2.setGravity(Gravity.CENTER);

            TextView col3 = new TextView(this);
            col3.setId(400 + i);
            col3.setText(String.valueOf(mf.getFootballers().get(i).getFootballerContact().getTelecom()));
            col3.setGravity(Gravity.CENTER);

            f.addView(col1);
            f.addView(col2);
            f.addView(col3);

            tabla.addView(f);
            colu = colu + 3;
        }
    }

    private static void changeTo(Context mContext, Class clase) {
        Intent intent = new Intent(mContext, clase);
        mContext.startActivity(intent);
    }
}