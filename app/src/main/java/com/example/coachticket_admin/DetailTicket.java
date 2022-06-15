package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Ticket;
import com.example.coachticket_admin.Model.Trip;
import com.example.coachticket_admin.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailTicket extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView start;
    private TextView finish;
    private TextView stationstart;
    private TextView stationfinish;
    private TextView departuretime;
    private TextView numAdult;
    private TextView numChild;
    private TextView user;
    private TextView seatNumber;
    private TextView breakfast;
    private TextView suitcase;
    private TextView code;

    private Ticket ticket;
    private Trip trip;
    private User username;
    private City city;

    private String tripid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket);
        getSupportActionBar().hide();

        start = findViewById(R.id.start);
        finish = findViewById(R.id.finish);
        stationstart = findViewById(R.id.stationstart);
        stationfinish = findViewById(R.id.stationfinish);
        departuretime = findViewById(R.id.departuretime);
        numAdult = findViewById(R.id.numAdult);
        numChild = findViewById(R.id.numChild);
        user = findViewById(R.id.user);
        seatNumber = findViewById(R.id.seatNumber);
        breakfast = findViewById(R.id.breakfast);
        suitcase = findViewById(R.id.suitcase);
        code = findViewById(R.id.code);

        Load();
    }
    private void Load(){
        db.collection("Tickets").document(AllTicket.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    ticket = doc.toObject(Ticket.class);
                    tripid = ticket.getTrip();

                    numAdult.setText(String.valueOf( ticket.getNumAdult()));
                    numChild.setText(String.valueOf(ticket.getNumChild()));

                    db.collection("Trips").document(ticket.getTrip())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot doc = task.getResult();
                                trip = doc.toObject(Trip.class);

                                start.setText(trip.getStart());
                                finish.setText(trip.getFinish());

                            }
                        }
                    });
                }
            }
        });
        /*db.collection("Trips").document(ticket.getTrip())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    trip = doc.toObject(Trip.class);

                    start.setText(ticket.getStart());
                    finish.setText(ticket.getFinish());

                }
            }
        });*/
    }
}