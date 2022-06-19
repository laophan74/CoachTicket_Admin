package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.coachticket_admin.Adapter.CityAdapter;
import com.example.coachticket_admin.Model.City;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllCity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RelativeLayout add;
    private ListView listView;
    private CityAdapter cityAdapter;

    private ArrayList<City> lCity = new ArrayList<>();
    private ImageView back;


    public static String document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_city);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);
        add = findViewById(R.id.add);
        back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllCity.this, Main.class));

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllCity.this, AddCity.class));

            }
        });
        GetAllCity();
    }
    private void GetAllCity(){
        db.collection("Cities").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                City city = doc.toObject(City.class);
                                city.setDocument(doc.getId());
                                lCity.add(city);
                            }
                            cityAdapter = new CityAdapter(AllCity.this, lCity);
                            listView.setAdapter(cityAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            City city = lCity.get(i);
            document = city.getDocument();
            startActivity(new Intent(AllCity.this, EditCity.class));
        });
    }
}