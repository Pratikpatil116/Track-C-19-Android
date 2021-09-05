package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {

    Button Vaccination, Report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        Vaccination = findViewById(R.id.vaccc);
        Report = findViewById(R.id.covid);






        }
        public void corona(View view)
        {
            Intent intent = new Intent(menu.this,dashboard.class);
            startActivity(intent);
            finish();

        }
        public void vaccination(View view){

            Intent intent = new Intent(menu.this,vaccine.class); // this vaccination dashboard not create yet its just function for understanding
            startActivity(intent);
            finish();

        }
    }
