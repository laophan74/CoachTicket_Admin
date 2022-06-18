package com.example.coachticket_admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.coachticket_admin.Model.Ticket;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CardView trip;
    private CardView coach;
    private RelativeLayout ticket;
    private RelativeLayout voucher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ticket = findViewById(R.id.ticket);
        trip = findViewById(R.id.trip);
        coach = findViewById(R.id.coach);
        voucher = findViewById(R.id.voucher);
        Button btnControlTicket = findViewById(R.id.btnControlT);
        btnControlTicket.setOnClickListener(view -> {
            startActivity(new Intent(Main.this, ControlTicketActivity.class));
        });

        voucher.setOnClickListener(view -> startActivity(new Intent(Main.this, AllVoucher.class)));
        ticket.setOnClickListener(view -> startActivity(new Intent(Main.this, AllTicket.class)));
        trip.setOnClickListener(view -> startActivity(new Intent(Main.this, AllTrip.class)));
        coach.setOnClickListener(view -> startActivity(new Intent(Main.this, AllCoach.class)));
    }

}