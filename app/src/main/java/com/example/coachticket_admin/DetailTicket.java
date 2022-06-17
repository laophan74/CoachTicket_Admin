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

import java.util.ArrayList;
import java.util.HashMap;

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
    private TextView meal;
    private TextView insurance;
    private TextView suitcase;
    private TextView code;
    private TextView totalcost;

    private Ticket ticket;
    private Trip trip;
    private User username;
    private City city;

    private HashMap<String, Boolean> service;
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
        meal = findViewById(R.id.meal);
        insurance = findViewById(R.id.insurance);
        code = findViewById(R.id.code);
        totalcost = findViewById(R.id.totalcost);

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
                    totalcost.setText(String.valueOf(ticket.getTotalCost()+"đ"));

                    //seat number
                    ArrayList<String> seatnumar = new ArrayList<>();
                    String seatnumstr = "";
                    seatnumar = ticket.getSeatNumber();
                    for(int i = 0; i<seatnumar.size();i++){
                        seatnumstr = seatnumstr +" "+ seatnumar.get(i);
                    }
                    seatNumber.setText(seatnumstr);

                    //service
                    service = ticket.getService();
                    Boolean x = service.get("breakfast");
                    Boolean y = service.get("meal");
                    Boolean z = service.get("insurance");
                    Boolean t = service.get("suitcase");
                    String x1,y1,z1,t1;
                    if(x==true)x1="Có";
                    else x1="Không";
                    if(y==true)y1="Có";
                    else y1="Không";
                    if(z==true)z1="Có";
                    else z1="Không";
                    if(t==true)t1="Có";
                    else t1="Không";
                    breakfast.setText(x1);
                    meal.setText(y1);
                    insurance.setText(z1);
                    suitcase.setText(t1);

                    db.collection("Trips").document(ticket.getTrip())
                            .get().addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    DocumentSnapshot doc1 = task1.getResult();
                                    trip = doc1.toObject(Trip.class);

                                    departuretime.setText(trip.getDeparture_time().toString());
                                    start.setText(trip.getStart());
                                    finish.setText(trip.getFinish());
                                    db.collection("Cities").document(trip.getStart())
                                            .get().addOnCompleteListener(task11 -> {
                                                if(task11.isSuccessful()){
                                                    DocumentSnapshot doc11 = task11.getResult();
                                                    city = doc11.toObject(City.class);

                                                    stationstart.setText("Bến xe: "+ city.getStation());
                                                }
                                            });
                                    db.collection("Cities").document(trip.getFinish())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                            if(task1.isSuccessful()){
                                                DocumentSnapshot doc1 = task1.getResult();
                                                city = doc1.toObject(City.class);

                                                stationfinish.setText("Bến xe: "+ city.getStation());
                                            }
                                        }
                                    });
                                    db.collection("Users").document(ticket.getUserID())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                            if(task1.isSuccessful()){
                                                DocumentSnapshot doc1 = task1.getResult();
                                                username = doc1.toObject(User.class);

                                                user.setText(username.getUsername());
                                            }
                                        }
                                    });

                                }
                            });

                }
            }
        });
    }
}