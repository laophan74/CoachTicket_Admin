package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.coachticket_admin.Adapter.CoachAdapter;
import com.example.coachticket_admin.Adapter.TicketAdapter;
import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.Ticket;
import com.example.coachticket_admin.Model.Trip;
import com.example.coachticket_admin.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllTicket extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RelativeLayout add;
    private ListView listView;
    private TicketAdapter ticketAdapter;

    private String a = "";
    private User user;

    private ArrayList<Ticket> lCoach = new ArrayList<>();

    public static String document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ticket);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);

        GetAllCoach();

    }

    private void GetAllCoach(){
        db.collection("Tickets").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Ticket coach = doc.toObject(Ticket.class);

                                /*db.collection("Users").document(coach.getUserID()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    user = task.getResult().toObject(User.class);
                                                    *//*a = user.getUsername();*//*
                                                    *//*db.collection("Trips").document(coach.getTrip()).get()
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    if(task.isSuccessful()){
                                                                        Trip trip = task.getResult().toObject(Trip.class);
                                                                        coach.setTrip(trip.getTripName());
                                                                    }
                                                                }
                                                            });*//*
                                                    coach.setUsername(user.getUsername());
                                                    coach.setDocument(doc.getId());
                                                    lCoach.add(coach);
                                                }
                                            }
                                        });*/

                                coach.setDocument(doc.getId());
                                lCoach.add(coach);
                            }
                            ticketAdapter = new TicketAdapter(AllTicket.this, lCoach);
                            listView.setAdapter(ticketAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ticket coach = lCoach.get(i);
                document = coach.getDocument();
                startActivity(new Intent(AllTicket.this, DetailTicket.class));
            }
        });
    }
}