package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditCoach extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView plate;
    private TextView detail;
    private TextView price;
    private TextView speed;
    private TextView seat1;
    private TextView seat2;
    private Button edit;
    private RelativeLayout delete;
    private ImageView back;

    private Coach coach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coach);
        getSupportActionBar().hide();

        plate = findViewById(R.id.plate);
        detail = findViewById(R.id.detail);
        price = findViewById(R.id.price);
        speed = findViewById(R.id.speed);
        seat1 = findViewById(R.id.seat1);
        seat2 = findViewById(R.id.seat2);
        edit = findViewById(R.id.editBtn);

        delete = findViewById(R.id.deleteCoach);
        back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditCoach.this, AllCoach.class));

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!price.getText().toString().matches("[0-9]+") ||
                        !speed.getText().toString().matches("[0-9]+") ||
                        !seat1.getText().toString().matches("[0-9]+") ||
                        !seat2.getText().toString().matches("[0-9]+") )
                {
                    Toast.makeText(EditCoach.this, "Nhập sai định dạng!", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(seat2.getText().toString()) != 0&&
                        Integer.parseInt(seat1.getText().toString()) != Integer.parseInt(seat2.getText().toString())){
                        Toast.makeText(EditCoach.this, "Số ghế 2 tầng phải bằng nhau", Toast.LENGTH_SHORT).show();
                }
                else if(price.getText().toString() == "" ||
                        speed.getText().toString() == "" ||
                        detail.getText().toString() == "" ||
                        plate.getText().toString() == "" ||
                        seat2.getText().toString() == "" ||
                        seat1.getText().toString() == "" ){
                    Toast.makeText(EditCoach.this, "Không được để trống", Toast.LENGTH_SHORT).show();

                }
                else {
                    EditBtnClick();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCoach();
            }
        });

        LoadCoach();
    }
    private void LoadCoach(){
        db.collection("TravelCars").document(AllCoach.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    coach = doc.toObject(Coach.class);
                    plate.setText(coach.getPlate());
                    detail.setText(coach.getDetail());
                    price.setText(String.valueOf(coach.getPrice()));
                    speed.setText(String.valueOf(coach.getSpeed()));
                    seat1.setText(String.valueOf(coach.getNumSeat1()));
                    seat2.setText(String.valueOf(coach.getNumSeat2()));

                }
            }
        });
    }

    private void EditBtnClick(){
        String Plate = plate.getText().toString();
        String Detail = detail.getText().toString();
        String Price = price.getText().toString();
        String Speed = speed.getText().toString();
        String Seat1 = seat1.getText().toString();
        String Seat2 = seat2.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("plate", Plate);
        docData.put("detail", Detail);
        docData.put("price", Integer.parseInt(Price));
        docData.put("speed", Integer.parseInt(Speed));
        //docData.put("seat1", s1);
        //docData.put("seat2", s2);
        docData.put("numSeat1", Integer.parseInt(Seat1));
        docData.put("numSeat2", Integer.parseInt(Seat2));

        db.collection("TravelCars").document(AllCoach.document)
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditCoach.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditCoach.this, AllCoach.class));

            }
        });

    }

    private void DeleteCoach(){
        AlertDialog.Builder altd = new AlertDialog.Builder(EditCoach.this);
        altd.setMessage("Bạn có muốn xoá?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("TravelCars").document(AllCoach.document)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditCoach.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditCoach.this, AllCoach.class));

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

}