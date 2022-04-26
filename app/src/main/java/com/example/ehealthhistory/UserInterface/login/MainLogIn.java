package com.example.ehealthhistory.UserInterface.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ehealthhistory.R;
import com.example.ehealthhistory.UserInterface.MainRoles;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class MainLogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageView medico = findViewById(R.id.app_image);
        medico.setImageResource(R.drawable.app_image_trans_small);

        final EditText username = findViewById(R.id.usernameLogIn);
        final EditText password = findViewById(R.id.passwordLogIn);
        final Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener((v -> {

            String usuario = username.getText().toString();
            String pass = password.getText().toString();

            if(usuario.length()>0 && pass.length()>0) FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(usuario, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, R.string.ok_usuario_contra, Toast.LENGTH_SHORT).show();
                            unShowVirtualKeyboard(password,v);

                            new Handler().postDelayed(() -> {
                                changeTo(v.getContext(), usuario);
                                username.setText("");
                                password.setText("");
                                }, 1000);

                        } else {
                            Toast.makeText(this, R.string.error_usuario_contra, Toast.LENGTH_SHORT).show();
                            password.setText("");
                        }
                    });
            else {
                Snackbar.make(findViewById(R.id.app_image), R.string.error_usuario_contra, Snackbar.LENGTH_SHORT).show();
                password.setText("");
            }
        }));
    }

    private static void changeTo(Context mContext, String username) {
        Intent intent = new Intent(mContext, MainRoles.class);
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