package com.example.ehealthhistory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ehealthhistory.R;
import com.example.ehealthhistory.ui.login.MainLogIn;

import java.util.Objects;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_inicio);
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(
                (Runnable) () -> {
                    Intent intent = new Intent(this, MainLogIn.class);
                    this.startActivity(intent);
                    finish();
                }, 2000);
    }
}

