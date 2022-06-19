package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button add;
    private EditText cname;
    private EditText distance;
    private EditText station;

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        getSupportActionBar().hide();

        cname = findViewById(R.id.cname);
        distance = findViewById(R.id.distance);
        station = findViewById(R.id.station);

        add = findViewById(R.id.addBtn);
        back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCity.this, AllCity.class));

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn();

            }
        });
    }
    private void addBtn() {
        String Cname = cname.getText().toString();
        String Station = station.getText().toString();
        String Distance = distance.getText().toString();


        Map<String, Object> docData = new HashMap<>();
        docData.put("cname", Cname);
        docData.put("distance", Integer.parseInt(Distance));
        docData.put("station", Station);

        db.collection("Cities").document(Cname)
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddCity.this, AllCity.class));
            }
        });

    }
}