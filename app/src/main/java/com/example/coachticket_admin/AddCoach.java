package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCoach extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView plate;
    private TextView detail;
    private TextView price;
    private TextView speed;
    private TextView seat1;
    private TextView seat2;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coach);
        getSupportActionBar().hide();

        plate = findViewById(R.id.plate);
        detail = findViewById(R.id.detail);
        price = findViewById(R.id.price);
        speed = findViewById(R.id.speed);
        seat1 = findViewById(R.id.seat1);
        seat2 = findViewById(R.id.seat2);
        add = findViewById(R.id.addBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!price.getText().toString().matches("[0-9]+") ||
                        !speed.getText().toString().matches("[0-9]+") ||
                        !seat1.getText().toString().matches("[0-9]+") ||
                        !seat2.getText().toString().matches("[0-9]+") )
                {
                    Toast.makeText(AddCoach.this, "Nhập sai định dạng!", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(seat2.getText().toString()) != 0&&
                        Integer.parseInt(seat1.getText().toString()) != Integer.parseInt(seat2.getText().toString())){
                    Toast.makeText(AddCoach.this, "Số ghế 2 tầng phải bằng nhau", Toast.LENGTH_SHORT).show();
                }
                else if(price.getText().toString() == "" ||
                        speed.getText().toString() == "" ||
                        detail.getText().toString() == "" ||
                        plate.getText().toString() == "" ||
                        seat2.getText().toString() == "" ||
                        seat1.getText().toString() == "" ){
                    Toast.makeText(AddCoach.this, "Không được để trống", Toast.LENGTH_SHORT).show();

                }
                else {
                    addBtn();
                }
            }
        });
    }

    private void addBtn(){
        String Plate = plate.getText().toString();
        String Detail = detail.getText().toString();
        String Price = price.getText().toString();
        String Speed = speed.getText().toString();
        String Seat1 = seat1.getText().toString();
        String Seat2 = seat2.getText().toString();
        ArrayList<Boolean> s1 = new ArrayList<>();
        ArrayList<Boolean> s2 = new ArrayList<>();
        /*for(int i = 0; i<Integer.parseInt(Seat1); i++){
            s1.add(false);
        }
        for(int j = 0; j<Integer.parseInt(Seat2); j++){
            s2.add(false);
        }*/
        Map<String, Object> docData = new HashMap<>();
        docData.put("plate", Plate);
        docData.put("detail", Detail);
        docData.put("price", Integer.parseInt(Price));
        docData.put("speed", Integer.parseInt(Speed));
        //docData.put("seat1", s1);
        //docData.put("seat2", s2);
        docData.put("numSeat1", Integer.parseInt(Seat1));
        docData.put("numSeat2", Integer.parseInt(Seat2));


        db.collection("TravelCars").document()
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCoach.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddCoach.this, AllCoach.class));

            }
        });
    }
}