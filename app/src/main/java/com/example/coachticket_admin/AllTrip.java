package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coachticket_admin.Adapter.TripAdapter;
import com.example.coachticket_admin.Model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ListView listView;
    private TripAdapter tripAdapter;

    private ArrayList<Trip> lTrip = new ArrayList<>();


    public static String document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trip);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);

        GetAllTrip();


    }

    private void GetAllTrip(){
        db.collection("Trips").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Trip trip = doc.toObject(Trip.class);
                                trip.setDocument(doc.getId());
                                lTrip.add(trip);
                            }
                            tripAdapter = new TripAdapter(AllTrip.this, lTrip);
                            listView.setAdapter(tripAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Trip trip = lTrip.get(i);
                document = trip.getDocument();
                startActivity(new Intent(AllTrip.this, AddTrip.class));

            }
        });
    }
}