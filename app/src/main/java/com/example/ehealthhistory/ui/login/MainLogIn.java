package com.example.ehealthhistory.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ehealthhistory.R;
import com.example.ehealthhistory.database.FireBase;
import com.example.ehealthhistory.ui.MainRoles;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainLogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        getSupportActionBar().hide();

        ImageView medico = findViewById(R.id.app_image);
        medico.setImageResource(R.drawable.app_image_trans_small);

        final EditText username = findViewById(R.id.usernameLogIn);
        final EditText password = findViewById(R.id.passwordLogIn);
        final Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener((v -> {

            String usuario = username.getText().toString();
            String pass = password.getText().toString();

            if(usuario.length()>0 && pass.length()>0) {

                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(usuario, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    changeTo(v.getContext(), usuario);
                                } else
                                    Snackbar.make(findViewById(R.id.loginButton), R.string.error_usuario_contra, Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
            else
                Snackbar.make(findViewById(R.id.loginButton), R.string.error_usuario_contra, Snackbar.LENGTH_SHORT).show();
        }));
    }

    private static void changeTo(Context mContext, String username) {
        Intent intent = new Intent(mContext, MainRoles.class);
        intent.putExtra("username", username);
        mContext.startActivity(intent);
    }
}