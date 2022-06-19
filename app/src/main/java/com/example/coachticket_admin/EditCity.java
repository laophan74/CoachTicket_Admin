package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.City;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditCity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView cname;
    private EditText station;
    private EditText distance;
    private ImageView back;
    private City city;
    private RelativeLayout delete;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);
        getSupportActionBar().hide();

        cname = findViewById(R.id.cname);
        station = findViewById(R.id.station);
        distance = findViewById(R.id.distance);

        editBtn = findViewById(R.id.editBtn);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.backPress);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditCity.this, AllCity.class));

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBtnClick();
            }
        });
        Load();
    }

    private void Load(){
        db.collection("Cities").document(AllCity.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    city = doc.toObject(City.class);
                    cname.setText(city.getCname());
                    station.setText(city.getStation());
                    distance.setText(String.valueOf(city.getDistance()));
                }
            }
        });
    }

    private void Delete(){
        AlertDialog.Builder altd = new AlertDialog.Builder(EditCity.this);
        altd.setMessage("Bạn có muốn xoá?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Cities").document(AllCity.document)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditCity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditCity.this, AllCity.class));

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert = altd.create();
        alert.setTitle("Cảnh báo!!!");
        alert.show();
    }

    private void EditBtnClick(){
        String Cname = cname.getText().toString();
        String Station = station.getText().toString();
        String Distance = distance.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("station", Station);
        docData.put("distance", Integer.parseInt(Distance));
        docData.put("cname", Cname);

        db.collection("Cities").document(AllCity.document)
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditCity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditCity.this, AllCity.class));

            }
        });

    }
}