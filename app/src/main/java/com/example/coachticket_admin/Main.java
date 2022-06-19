package com.example.coachticket_admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.Ticket;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.security.CryptoPrimitive;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CardView trip;
    private CardView coach;
    private CardView city;
    private RelativeLayout ticket;
    private RelativeLayout voucher;
    private RelativeLayout map;

    public static ArrayList<City> lCity = new ArrayList<>();
    public static ArrayList<String> lDist = new ArrayList<>();
    public static ArrayList<Coach> lCoach = new ArrayList<>();
    public static ArrayList<String> lDist1 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ticket = findViewById(R.id.ticket);
        trip = findViewById(R.id.trip);
        city = findViewById(R.id.cityCard);
        coach = findViewById(R.id.coach);
        voucher = findViewById(R.id.voucher);
        map = findViewById(R.id.mapp);

        ImageView btnControlTicket = findViewById(R.id.btnControlT);
        btnControlTicket.setOnClickListener(view -> {
            startActivity(new Intent(Main.this, ControlTicketActivity.class));
        });
        if(lCity.size()==0){
            LoadSpinner();
        }
        city.setOnClickListener(view -> startActivity(new Intent(Main.this, AllCity.class)));
        voucher.setOnClickListener(view -> startActivity(new Intent(Main.this, AllVoucher.class)));
        ticket.setOnClickListener(view -> startActivity(new Intent(Main.this, AllTicket.class)));
        trip.setOnClickListener(view -> startActivity(new Intent(Main.this, AllTrip.class)));
        coach.setOnClickListener(view -> startActivity(new Intent(Main.this, AllCoach.class)));
        map.setOnClickListener(view -> startActivity(new Intent(Main.this, MMap.class)));

    }

    private void LoadSpinner() {
        db.collection("Cities").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    City city = doc.toObject(City.class);
                                    lCity.add(city);
                                }
                                Collections.sort(lCity);
                                for (City item : lCity) {
                                    lDist.add(item.getCname());
                                }
                            }
                        }
                );
        db.collection("TravelCars").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Coach coach = doc.toObject(Coach.class);
                                    lCoach.add(coach);
                                }
                                for (Coach item : lCoach) {
                                    lDist1.add(item.getPlate());
                                }
                            }
                        }
                );
    }

}