package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Main extends AppCompatActivity {

    private CardView trip;
    private CardView coach;
    private RelativeLayout ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ticket = findViewById(R.id.ticket);
        trip = findViewById(R.id.trip);
        coach = findViewById(R.id.coach);

        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, AllTicket.class));

            }
        });
        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, AllTrip.class));

            }
        });
        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, AllCoach.class));

            }
        });
    }
}