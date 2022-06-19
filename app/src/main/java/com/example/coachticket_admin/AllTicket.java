package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class AllTicket extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RelativeLayout add;
    private ListView listView;
    private TicketAdapter ticketAdapter;
    private ImageView back;

    private String a = "";
    private User user;

    private ArrayList<Ticket> lCoach = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();

    public static String document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ticket);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);
        back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllTicket.this, Main.class));

            }
        });
        GetAllCoach();
    }

    private void GetAllCoach(){
        db.collection("Tickets").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Ticket ticket = doc.toObject(Ticket.class);

                                db.collection("Trips").document(ticket.getTrip())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Trip trip = task.getResult().toObject(Trip.class);
                                        ticket.setTripName(trip.getTripName());
                                    }
                                });
                                db.collection("Users").document(ticket.getUserID())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        User user = task.getResult().toObject(User.class);
                                        ticket.setUsername(user.getUsername());
                                    }
                                });

                                ticket.setDocument(doc.getId());
                                lCoach.add(ticket);
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