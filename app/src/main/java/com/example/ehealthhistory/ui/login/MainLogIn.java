package com.example.ehealthhistory.ui.login;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ehealthhistory.R;
import com.example.ehealthhistory.checks.CheckPassword;
import com.example.ehealthhistory.checks.CheckUsername;
import com.example.ehealthhistory.database.SQLiteHelper;
import com.example.ehealthhistory.ui.CareTeam.MainCareTeam;
import com.example.ehealthhistory.ui.Foootballer.MainFootballer;
import com.example.ehealthhistory.ui.Club.MainClub;
import com.example.ehealthhistory.ui.MainRoles;
import com.google.android.material.snackbar.Snackbar;


public class MainLogIn extends AppCompatActivity {

    SQLiteHelper datebase;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        getSupportActionBar().hide();

        ImageView medico = findViewById(R.id.app_image);
        medico.setImageResource(R.drawable.app_image_trans_small);

        datebase = new SQLiteHelper(MainLogIn.this);
        db = datebase.getWritableDatabase();

        final EditText username = (EditText) findViewById(R.id.usernameLogIn);
        final EditText password = findViewById(R.id.passwordLogIn);
        final Button loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = username.getText().toString();

                if(CheckUsername.check(username) && CheckPassword.check(password)) {
                    changeTo(v.getContext(), MainRoles.class, usuario);
                }
                else
                    Snackbar.make(findViewById(R.id.loginButton), R.string.error_usuario_contra, Snackbar.LENGTH_SHORT).show();
            }

        }));
    }

    private static void changeTo(Context mContext, Class clase, String username) {
        Intent intent = new Intent(mContext, clase);
        intent.putExtra("username", username);
        mContext.startActivity(intent);
    }
}