package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.Model.Coach;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button addBtn;
    private ImageButton datePicker;

    private TextView dateTextview;

    private Spinner city1;
    private Spinner city2;
    private Spinner coach;

    private ArrayList<City> lCity = new ArrayList<>();
    private ArrayList<String> lDist = new ArrayList<>();
    private ArrayList<Coach> lCoach = new ArrayList<>();
    private ArrayList<String> lDist1 = new ArrayList<>();

    private City cStart;
    private City cEnd;
    private Coach coachName;
    private Calendar calendar;

    private String dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getSupportActionBar().hide();

        addBtn = findViewById(R.id.addBtn);
        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        coach = findViewById(R.id.coach);

        dateTextview = findViewById(R.id.dateTextview);
        datePicker = findViewById(R.id.datePickerActions);
        calendar = Calendar.getInstance();
        SetDateTextView();

        addBtn.setOnClickListener(view -> addBtnClick());

        datePicker.setOnClickListener(view -> ShowDateTimePicker());

        LoadCoachs();
        LoadCities();
    }

    private void LoadCities() {
        db.collection("Cities").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    City city = doc.toObject(City.class);
                                    lCity.add(city);
                                }
                                Collections.sort(lCity);
                                for (City item : lCity) {
                                    lDist.add(item.getCname());
                                }
                                ArrayAdapter<String> aaC = new ArrayAdapter<>(AddTrip.this,
                                        android.R.layout.simple_spinner_dropdown_item, lDist);
                                city1.setAdapter(aaC);
                                city1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        cStart = ReturnCity(city1.getSelectedItem().toString());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                city2.setAdapter(aaC);
                                city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        cEnd = ReturnCity(city2.getSelectedItem().toString());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        }
                );
    }

    private City ReturnCity(String x) {
        for (City item : lCity) {
            if (x == item.getCname()) return item;
        }
        return null;
    }

    private void LoadCoachs() {
        db.collection("TravelCars").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Coach coach = doc.toObject(Coach.class);
                                    lCoach.add(coach);
                                }
                                for (Coach item : lCoach) {
                                    lDist1.add(item.getPlate());
                                }
                                ArrayAdapter<String> aaC1 = new ArrayAdapter<>(AddTrip.this,
                                        android.R.layout.simple_spinner_dropdown_item, lDist1);
                                coach.setAdapter(aaC1);
                                coach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        coachName = ReturnCoach(coach.getSelectedItem().toString());
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });

                            }
                        }
                );
    }
    private Coach ReturnCoach(String x) {
        for (Coach item : lCoach) {
            if (Objects.equals(x, item.getPlate())) return item;
        }
        return null;
    }
    private void addBtnClick(){

        String start = cStart.getCname();
        String finish = cEnd.getCname();
        Date date = calendar.getTime();
        Timestamp ts = new Timestamp(date);
        String coach = coachName.getPlate();
        ArrayList<Boolean> seat1 = new ArrayList<>();
        ArrayList<Boolean> seat2 = new ArrayList<>();

        for(int i = 0; i < coachName.getNumSeat1(); i++){
            seat1.add(false);
        }
        for(int i = 0; i < coachName.getNumSeat2(); i++){
            seat2.add(false);
        }
        int avai = coachName.getNumSeat1() + coachName.getNumSeat2();
        Map<String, Object> docData = new HashMap<>();
        docData.put("tripName", start+" - " + finish);
        docData.put("start", start);
        docData.put("finish", finish);
        docData.put("departure_time", ts);
        docData.put("coach", coach);
        docData.put("isDone", false);
        docData.put("seat1", seat1);
        docData.put("seat2", seat2);
        docData.put("available", avai);

        DocumentReference addRef = db.collection("Trips").document();
        addRef.set(docData).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                addRef.update("tripID", addRef.getId());
                Toast.makeText(AddTrip.this, "Thêm chuyến thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTrip.this, AllTrip.class));
            }
        });
    }

    private void ShowDateTimePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, t, t1) -> {
                calendar.set(Calendar.HOUR_OF_DAY, t);
                calendar.set(Calendar.MINUTE, t1);
                SetDateTextView();
            };
            new TimePickerDialog(this, timeSetListener, 0, 0, false).show();
        };
        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void SetDateTextView(){
        String pickUp =
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " "
                + calendar.get(Calendar.DAY_OF_MONTH) + "/"
                +(calendar.get(Calendar.MONTH)+1) + "/"
                + calendar.get(Calendar.YEAR);
        dateTextview.setText(pickUp);
    }
}