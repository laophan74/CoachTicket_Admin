package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Trip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        getSupportActionBar().hide();

    }
}