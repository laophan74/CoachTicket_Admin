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
import com.example.coachticket_admin.Model.Coach;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllCoach extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RelativeLayout add;
    private ListView listView;
    private CoachAdapter coachAdapter;

    private ArrayList<Coach> lCoach = new ArrayList<>();

    public static String document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_coach);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);
        add = findViewById(R.id.addCoach);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllCoach.this, AddCoach.class));
            }
        });
        GetAllCoach();

    }

    private void GetAllCoach(){
        db.collection("TravelCars").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Coach coach = doc.toObject(Coach.class);
                                coach.setDocument(doc.getId());
                                lCoach.add(coach);
                            }
                            coachAdapter = new CoachAdapter(AllCoach.this, lCoach);
                            listView.setAdapter(coachAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Coach coach = lCoach.get(i);
                document = coach.getDocument();
                startActivity(new Intent(AllCoach.this, EditCoach.class));
            }
        });
    }
}