package com.example.ehealthhistory;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_base);
                Objects.requireNonNull(getSupportActionBar()).hide();

                ImageView medico = findViewById(R.id.imageView);
                medico.setImageResource(R.drawable.app_image_trans_small);
        }

}
